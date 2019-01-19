create table resource (
  id int PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(120) NOT NULL,
  last_name VARCHAR(120) NOT NULL,
  resource_type VARCHAR(120) REFERENCES resource_type(type)
);