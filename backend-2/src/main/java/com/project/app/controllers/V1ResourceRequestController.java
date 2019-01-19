package com.project.app.controllers;

import com.project.app.models.entities.Patient;
import com.project.app.models.entities.Resource;
import com.project.app.models.entities.ResourceRequest;
import com.project.app.models.v1.request.V1ProcedureRequest;
import com.project.app.repositories.PatientRepository;
import com.project.app.repositories.ResourceRepository;
import com.project.app.repositories.ResourceRequestRepository;
import com.project.app.repositories.ResourceTypeRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/procedure")
public class V1ResourceRequestController {

    private final ResourceRequestRepository resourceRequestRepository;
    private final ResourceRepository resourceRepository;
    private final PatientRepository patientRepository;
    private final ResourceTypeRepository resourceTypeRepository;

    @Autowired
    public V1ResourceRequestController(ResourceRequestRepository resourceRequestRepository, ResourceRepository resourceRepository, PatientRepository patientRepository, ResourceTypeRepository resourceTypeRepository) {
        this.resourceRequestRepository = resourceRequestRepository;
        this.resourceRepository = resourceRepository;
        this.patientRepository = patientRepository;
        this.resourceTypeRepository = resourceTypeRepository;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void createProcedure(@RequestBody @NotNull @Valid V1ProcedureRequest request) {
        val patient = patientRepository.findById(request.getPatientId()).get();
        for (ResourceRequest rq : patient.getResourceRequests()) {
            rq.setState("completed");
        }
        patientRepository.save(patient);


        val type = resourceTypeRepository.findById(request.getResource().toLowerCase()).get();
        val hasResource = patient.getAssignedResources().stream()
                .filter(x -> x.getType().getType().equals(request.getResource().toLowerCase()))
                .collect(Collectors.toList());


        Resource resource = hasResource.size() > 0 ? hasResource.get(0) : null;
        if (hasResource.size() == 0) {
            val resources = resourceRepository.getResourcesByType(type);
            Resource lowest = null;
            for (Resource rsc : resources) {
                if (lowest == null) {
                    lowest = rsc;
                } else {
                    if (rsc.getPatients().stream().filter(Patient::isActive).count() <
                            lowest.getPatients().stream().filter(Patient::isActive).count()) {
                        lowest = rsc;
                    }
                }
            }
            resource = lowest;
        }


        patient.getAssignedResources().add(resource);

        val resourceRequest = ResourceRequest.builder()
                .override(false)
                .patient(patient)
                .procedureName(request.getProcedureName())
                .resource(resource)
                .state("pending")
                .timeRequested(LocalDateTime.now())
                .rating(request.getRating().intValue())
                .notes(request.getNotes())
                .build();

        resourceRequestRepository.save(resourceRequest);

        patientRepository.save(patient);
    }


}
