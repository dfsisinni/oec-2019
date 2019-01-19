CREATE TABLE user (
  username VARCHAR(120) NOT NULL PRIMARY KEY,
  password VARCHAR(120) NOT NULL,
  corresponding_id INT NOT NULL,
  type VARCHAR(120) NOT NULL
);