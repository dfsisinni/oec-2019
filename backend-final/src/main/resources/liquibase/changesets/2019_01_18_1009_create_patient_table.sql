CREATE TABLE patients (
  id int PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(120) NOT NULL,
  last_name VARCHAR(120) NOT NULL,
  reason_for_vist VARCHAR(120) NOT NULL,
  admission_time TIMESTAMP NOT NULL,
  last_assessment_time TIMESTAMP NOT NULL,
  active BOOLEAN NOT NULL,
  floor VARCHAR(120) NOT NULL
);