DROP TABLE test_point;

CREATE TABLE test_point (
  id INT PRIMARY KEY,
  geometry GEOMETRY(Point,4326)
);