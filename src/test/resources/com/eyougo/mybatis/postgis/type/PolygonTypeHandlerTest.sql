DROP TABLE test_polygon;

CREATE TABLE test_polygon (
  id INT PRIMARY KEY,
  geometry GEOMETRY(POLYGON,4326)
);