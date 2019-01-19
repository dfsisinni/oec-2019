package com.project.app.repositories;

import com.project.app.models.entities.Patient;
import com.project.app.models.entities.Resource;
import com.project.app.models.entities.ResourceRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResourceRequestRepository extends CrudRepository<ResourceRequest, Long> {

    List<ResourceRequest> findResourceRequestByResource(Resource resource);

    List<ResourceRequest> findResourceRequestByPatient(Patient patient);

}
