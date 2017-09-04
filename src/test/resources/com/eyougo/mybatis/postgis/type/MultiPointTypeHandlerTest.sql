DROP TABLE test_multipoint;

CREATE TABLE test_multipoint (
  id INT PRIMARY KEY,
  geometry GEOMETRY(MULTIPOINT,4326)
);