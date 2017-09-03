package com.eyougo.mybatis.postgis.type;

import com.eyougo.mybatis.BaseDataTest;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.postgis.Geometry;
import org.postgis.PGgeometry;

import javax.sql.DataSource;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by mei on 31/08/2017.
 */
public abstract class AbstractGeometryTypeHandlerTest<T extends Geometry, E extends AbstractGeometryTypeHandlerTest.GeometryEntity<T>> extends BaseTypeHandlerTest {

    protected static final int SRID = 4326;

    protected static SqlSessionFactory sqlSessionFactory;

    protected T t;

    protected String table;
    @Mock
    protected PGgeometry pGgeometry;

    protected abstract TypeHandler<T> getTypeHandler();

    public static void setupSqlSessionFactory(String initSqlPath) throws Exception {
        DataSource dataSource = BaseDataTest.createUnpooledDataSource("jdbc.properties");
        BaseDataTest.runScript(dataSource, initSqlPath);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("Production", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    @Override
    @Test
    public void shouldSetParameter() throws Exception {
        getTypeHandler().setParameter(ps, 1, t, null);
        ArgumentCaptor<PGgeometry> argument = ArgumentCaptor.forClass(PGgeometry.class);
        verify(ps).setObject(eq(1), argument.capture());
        assertEquals(t, argument.getValue().getGeometry());
    }

    @Override
    @Test
    public void shouldGetResultFromResultSetByName() throws Exception {
        when(rs.getObject("column")).thenReturn(pGgeometry);
        when(rs.wasNull()).thenReturn(false);
        when(pGgeometry.getGeometry()).thenReturn(t);
        assertEquals(t, getTypeHandler().getResult(rs, "column"));
    }

    @Override
    @Test
    public void shouldGetResultNullFromResultSetByName() throws Exception {
        when(rs.getObject("column")).thenReturn(null);
        when(rs.wasNull()).thenReturn(true);
        assertNull(getTypeHandler().getResult(rs, "column"));
    }

    @Override
    @Test
    public void shouldGetResultFromResultSetByPosition() throws Exception {
        when(rs.getObject(1)).thenReturn(pGgeometry);
        when(rs.wasNull()).thenReturn(false);
        when(pGgeometry.getGeometry()).thenReturn(t);
        assertEquals(t, getTypeHandler().getResult(rs, 1));
    }

    @Override
    @Test
    public void shouldGetResultNullFromResultSetByPosition() throws Exception {
        when(rs.getObject(1)).thenReturn(null);
        when(rs.wasNull()).thenReturn(true);
        assertNull(getTypeHandler().getResult(rs, 1));
    }

    @Override
    @Test
    public void shouldGetResultFromCallableStatement() throws Exception {
        when(cs.getObject(1)).thenReturn(pGgeometry);
        when(cs.wasNull()).thenReturn(false);
        when(pGgeometry.getGeometry()).thenReturn(t);
        assertEquals(t, getTypeHandler().getResult(cs, 1));
    }

    @Override
    @Test
    public void shouldGetResultNullFromCallableStatement() throws Exception {
        when(cs.getObject(1)).thenReturn(null);
        when(cs.wasNull()).thenReturn(true);
        assertNull(getTypeHandler().getResult(cs, 1));
    }

    @Test
    public void integrationTest() throws IOException {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            Mapper mapper = (Mapper) session.getMapper(getMapperClass());
            // insert (Geometry -> DB)
            {
                GeometryEntity geometryEntity = new GeometryEntity<T>();
                geometryEntity.setId(1);
                geometryEntity.setGeometry(t);
                mapper.insert(table, geometryEntity);
                session.commit();
            }
            // select (DB -> Geometry)
            {
                E e = (E)mapper.findOne(table, 1);
                assertEquals(t, e.getGeometry());
                assertEquals(SRID, e.getGeometry().getSrid());
            }
        } finally {
            session.close();
        }

    }

    protected abstract <T> Class<T> getMapperClass();

    interface Mapper<E> {
        @Select("SELECT id, geometry FROM ${table} WHERE id = #{id}")
        @Results ({
                @Result(property = "id", column = "id"),
                @Result(property = "geometry", column = "geometry")
        })
        E findOne(@Param("table") String table, @Param("id") int id);

        @Insert("INSERT INTO ${table}(id, geometry) VALUES(#{geometryEntity.id}, #{geometryEntity.geometry})")
        void insert(@Param("table") String table, @Param("geometryEntity") GeometryEntity geometryEntity);
    }

    static class GeometryEntity<T extends Geometry> {
        private int id;
        private T geometry;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public T getGeometry() {
            return geometry;
        }

        public void setGeometry(T geometry) {
            this.geometry = geometry;
        }
    }
}
