package com.eyougo.mybatis.postgis.type;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.junit.Before;
import org.junit.BeforeClass;
import org.postgis.MultiPoint;
import org.postgis.Point;

/**
 * Created by mei on 31/08/2017.
 */
public class MultiPointTypeHandlerTest extends AbstractGeometryTypeHandlerTest<MultiPoint, MultiPointTypeHandlerTest.MultiPointEntity> {

    private static final TypeHandler<MultiPoint> TYPE_HANDLER = new MultiPointTypeHandler();

    @BeforeClass
    public static void setUp() throws Exception{
        setupSqlSessionFactory("com/eyougo/mybatis/postgis/type/MultiPointTypeHandlerTest.sql");
        Configuration configuration = sqlSessionFactory.getConfiguration();
        configuration.getTypeHandlerRegistry().register(MultiPointTypeHandler.class);
        configuration.addMapper(MultiPointMapper.class);
    }

    @Before
    public void before() {
        table = "test_multipoint";

        Point[] points = new Point[4];
        points[0] = new Point(123.45d, 23.45d);
        points[1] = new Point(124.45d, 23.45d);
        points[2] = new Point(124.45d, 24.45d);
        points[3] = new Point(123.45d, 24.45d);

        t = new MultiPoint(points);
        t.setSrid(SRID);
    }

    @Override
    protected TypeHandler<MultiPoint> getTypeHandler() {
        return TYPE_HANDLER;
    }

    @Override
    protected Class<MultiPointMapper> getMapperClass() {
        return MultiPointMapper.class;
    }

    interface MultiPointMapper extends Mapper<MultiPointEntity> {

    }

    static class MultiPointEntity extends AbstractGeometryTypeHandlerTest.GeometryEntity<MultiPoint> {

    }

}
