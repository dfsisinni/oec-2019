CREATE TABLE resource_request (
  id int PRIMARY KEY AUTO_INCREMENT,
  procedure_name VARCHAR(120) NOT NULL,
  time_requested TIMESTAMP NOT NULL,
  patient_id int NOT NULL REFERENCES patients(id),
  resource_id int NOT NULL REFERENCES resources(id),
  state VARCHAR(120) NOT NULL,
  rating int NOT NULL,
  override BOOLEAN NOT NULL
);