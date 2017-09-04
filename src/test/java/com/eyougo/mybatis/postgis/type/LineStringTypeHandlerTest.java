package com.eyougo.mybatis.postgis.type;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.junit.Before;
import org.junit.BeforeClass;
import org.postgis.LineString;
import org.postgis.Point;

/**
 * Created by mei on 31/08/2017.
 */
public class LineStringTypeHandlerTest extends AbstractGeometryTypeHandlerTest<LineString, LineStringTypeHandlerTest.LineStringEntity> {

    private static final TypeHandler<LineString> TYPE_HANDLER = new LineStringTypeHandler();

    @BeforeClass
    public static void setUp() throws Exception{
        setupSqlSessionFactory("com/eyougo/mybatis/postgis/type/LineStringTypeHandlerTest.sql");
        Configuration configuration = sqlSessionFactory.getConfiguration();
        configuration.getTypeHandlerRegistry().register(LineStringTypeHandler.class);
        configuration.addMapper(LineStringMapper.class);
    }

    @Before
    public void before() {
        table = "test_linestring";

        Point[] points = new Point[4];
        points[0] = new Point(123.45d, 23.45d);
        points[1] = new Point(124.45d, 23.45d);
        points[2] = new Point(124.45d, 24.45d);
        points[3] = new Point(123.45d, 24.45d);

        t = new LineString(points);
        t.setSrid(SRID);
    }

    @Override
    protected TypeHandler<LineString> getTypeHandler() {
        return TYPE_HANDLER;
    }

    @Override
    protected Class<LineStringMapper> getMapperClass() {
        return LineStringMapper.class;
    }

    interface LineStringMapper extends Mapper<LineStringEntity> {

    }

    static class LineStringEntity extends AbstractGeometryTypeHandlerTest.GeometryEntity<LineString> {

    }

}
