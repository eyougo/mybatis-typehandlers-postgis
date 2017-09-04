DROP TABLE test_linestring;

CREATE TABLE test_linestring (
  id INT PRIMARY KEY,
  geometry GEOMETRY(LINESTRING,4326)
);