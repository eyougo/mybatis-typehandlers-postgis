# MyBatis Type Handlers for PostGIS

[![Build Status](https://travis-ci.org/eyougo/mybatis-typehandlers-postgis.svg?branch=master)](https://travis-ci.org/eyougo/mybatis-typehandlers-postgis)

The MyBatis type handlers supporting geometry types introduced in PostGIS: JDBC Geometry API


## Requirements

Java 7 or higher.

The latest PostGIS JDBC API which it depends on was using the JRE7 version of PostgreSQL JDBC, so it requires Java 7 or higher.

## Configuration

* If you are using MyBatis alone, add the type handlers to your `mybatis-config.xml` as follow:

```xml
<typeHandlers>
  <!-- ... -->
  <typeHandler handler="com.eyougo.mybatis.postgis.type.PointTypeHandler" />
  <typeHandler handler="com.eyougo.mybatis.postgis.type.PolygonTypeHandler" />
</typeHandlers>
```
* If you are using MyBatis with **Spring**, add the type handlers package to the Spring configuration as follow:

With XML Configuration 

```xml
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <!-- ... -->
    <property name="typeAliasesPackage" value="com.eyougo.mybatis.postgis.type" />
</bean>
```
Or with Java configuration

```java
@Bean
public SqlSessionFactory sqlSessionFactory(Configuration config) {
    SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
    // ...
    factory.setTypeHandlersPackage("com.eyougo.mybatis.postgis.type");
    return factory.getObject();
}
```
* If you are using MyBatis with **Spring Boot**, add the type handlers package to the configuration file as follow: 

`application.properties`

```properties
mybatis.type-handlers-package = com.eyougo.mybatis.postgis.type
```
Or `application.yml`

```yaml
mybatis:
    type-handlers-package: com.eyougo.mybatis.postgis.type

```

## Supported types

The following type handlers are supported:

| Type handler |  PostGIS Geometry API type | JDBC types | Available version |
| ------------ | ----------------------- | ---------- | :------------------: | 
| `PointTypeHandler` | `org.postgis.Point` | `OTHER` |  1.0 |
| `PolygonTypeHandler` | `org.postgis.Polygon` | `OTHER` |  1.0 |
| `LineStringTypeHandler` | `org.postgis.LineString` | `OTHER` |  1.0 |
| `MultiPointTypeHandler` | `org.postgis.MultiPoint` | `OTHER` |  1.0 |


> **Note:**
> For more details of type handler, please refer to "[MyBatis 3 REFERENCE DOCUMENTATION](http://www.mybatis.org/mybatis-3/configuration.html#typeHandlers)".