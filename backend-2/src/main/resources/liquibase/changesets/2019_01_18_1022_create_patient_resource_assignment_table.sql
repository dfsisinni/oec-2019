CREATE TABLE patient_resource_assignment (
  resource_id INT NOT NULL REFERENCES resources(id),
  patient_id INT NOT NULL REFERENCES patients(id),
  PRIMARY KEY(resource_id, patient_id)
);