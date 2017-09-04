package com.eyougo.mybatis.postgis.type;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.junit.Before;
import org.junit.BeforeClass;
import org.postgis.LinearRing;
import org.postgis.Point;
import org.postgis.Polygon;

/**
 * Created by mei on 31/08/2017.
 */
public class PolygonTypeHandlerTest extends AbstractGeometryTypeHandlerTest<Polygon, PolygonTypeHandlerTest.PolygonEntity> {

    private static final TypeHandler<Polygon> TYPE_HANDLER = new PolygonTypeHandler();

    @BeforeClass
    public static void setUp() throws Exception{
        setupSqlSessionFactory("com/eyougo/mybatis/postgis/type/PolygonTypeHandlerTest.sql");
        Configuration configuration = sqlSessionFactory.getConfiguration();
        configuration.getTypeHandlerRegistry().register(PolygonTypeHandler.class);
        configuration.addMapper(PolygonMapper.class);
    }

    @Before
    public void before() {
        table = "test_polygon";

        Point[] points = new Point[5];
        points[0] = new Point(123.45d, 23.45d);
        points[1] = new Point(124.45d, 23.45d);
        points[2] = new Point(124.45d, 24.45d);
        points[3] = new Point(123.45d, 24.45d);
        points[4] = new Point(123.45d, 23.45d);
        LinearRing linearRing = new LinearRing(points);
        t = new Polygon(new LinearRing[]{linearRing});
        t.setSrid(SRID);
    }

    @Override
    protected TypeHandler<Polygon> getTypeHandler() {
        return TYPE_HANDLER;
    }

    @Override
    protected Class<PolygonMapper> getMapperClass() {
        return PolygonMapper.class;
    }

    interface PolygonMapper extends Mapper<PolygonEntity> {

    }

    static class PolygonEntity extends AbstractGeometryTypeHandlerTest.GeometryEntity<Polygon> {

    }

}
