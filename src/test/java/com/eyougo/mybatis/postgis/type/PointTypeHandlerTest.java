package com.eyougo.mybatis.postgis.type;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.junit.Before;
import org.junit.BeforeClass;
import org.postgis.Point;

/**
 * Created by mei on 31/08/2017.
 */
public class PointTypeHandlerTest extends AbstractGeometryTypeHandlerTest<Point, PointTypeHandlerTest.PointEntity> {

    private static final TypeHandler<Point> TYPE_HANDLER = new PointTypeHandler();

    @BeforeClass
    public static void setUp() throws Exception{
        setupSqlSessionFactory("com/eyougo/mybatis/postgis/type/PointTypeHandlerTest.sql");
        Configuration configuration = sqlSessionFactory.getConfiguration();
        configuration.getTypeHandlerRegistry().register(PointTypeHandler.class);
        configuration.addMapper(PointMapper.class);
    }

    @Before
    public void before() {
        table = "test_point";
        t = new Point(123.45d, 23.45d);
        t.setSrid(SRID);
    }

    @Override
    protected TypeHandler<Point> getTypeHandler() {
        return TYPE_HANDLER;
    }

    @Override
    protected Class<PointMapper> getMapperClass() {
        return PointMapper.class;
    }

    interface PointMapper extends Mapper<PointEntity> {

    }

    static class PointEntity extends AbstractGeometryTypeHandlerTest.GeometryEntity<Point> {

    }

}
