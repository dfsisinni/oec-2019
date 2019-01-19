package com.project.app.controllers;

import com.project.app.models.entities.Patient;
import com.project.app.models.entities.Resource;
import com.project.app.models.entities.ResourceRequest;
import com.project.app.models.v1.request.V1PatientRequest;
import com.project.app.models.v1.response.V1PatientResponse;
import com.project.app.models.v1.response.V1ProcedureResponse;
import com.project.app.repositories.PatientRepository;
import com.project.app.repositories.ResourceRepository;
import com.project.app.repositories.ResourceRequestRepository;
import com.project.app.repositories.ResourceTypeRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patient")
public class V1PatientController {

    private final ResourceRepository resourceRepository;
    private final ResourceTypeRepository resourceTypeRepository;
    private final PatientRepository patientRepository;

    private final ResourceRequestRepository resourceRequestRepository;

    @Autowired
    public V1PatientController(PatientRepository patientRepository, ResourceRepository resourceRepository,
                               ResourceTypeRepository resourceTypeRepository, ResourceRequestRepository resourceRequestRepository) {
        this.patientRepository = patientRepository;
        this.resourceRepository = resourceRepository;
        this.resourceTypeRepository = resourceTypeRepository;
        this.resourceRequestRepository = resourceRequestRepository;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public V1PatientResponse createPatient(@RequestBody @Valid @NotNull V1PatientRequest request) {

            val resource = resourceRepository.findById(request.getResourceId()).get();
            val listOfResources = new ArrayList<Resource>();
            listOfResources.add(resource);


        val resourceType = resourceTypeRepository.findById("hospitalist").get();
        val resources = resourceRepository.getResourcesByType(resourceType);

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


        val patient = Patient.builder()
                .active(true)
                .admissionTime(LocalDateTime.now())
                .assignedResources(listOfResources)
                .firstName(request.getFirstName())
                .floor(request.getFloor())
                .lastAssessmentTime(LocalDateTime.now())
                .lastName(request.getLastName())
                .reasonForVisit(request.getReasonForVisit())
                .resourceRequests(new ArrayList<>())
                .build();
        patientRepository.save(patient);

        val resourceRequest = ResourceRequest.builder()
                .override(false)
                .patient(patient)
                .procedureName("Assessment")
                .resource(lowest)
                .state("pending")
                .timeRequested(LocalDateTime.now())
                .rating(3)
                .resource(lowest)
                .notes("Initial Notes")
                .build();

        resourceRequestRepository.save(resourceRequest);

        return V1PatientResponse.builder()
                .admissionTime(patient.getAdmissionTime())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .floor(patient.getFloor())
                .lastAssessmentTime(patient.getLastAssessmentTime())
                .procedureResponses(patient.getResourceRequests().stream()
                        .map(x -> V1ProcedureResponse.builder()
                                .procedureName(x.getProcedureName())
                                .timeRequested(x.getTimeRequested())
                                .build()).collect(Collectors.toList()))
                .reasonForVisit(patient.getReasonForVisit())
                .build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public V1PatientResponse getPatient(@PathVariable("id") @NotNull Long id) {
        val patient = patientRepository.findById(id).get();

        val resourceRequests = resourceRequestRepository.findResourceRequestByPatient(patient);

        val y = V1PatientResponse.builder()
                .admissionTime(patient.getAdmissionTime())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .floor(patient.getFloor())
                .lastAssessmentTime(patient.getLastAssessmentTime())
                .procedureResponses(resourceRequests.stream()
                        .map(x -> V1ProcedureResponse.builder()
                                .procedureName(x.getProcedureName())
                                .status(x.getState())
                                .timeRequested(x.getTimeRequested())
                                .doctor(x.getResource().getFirstName() + " " + x.getResource().getLastName())
                                .notes(x.getNotes())
                                .build())
                        .collect(Collectors.toList()))
                .reasonForVisit(patient.getReasonForVisit())
                .build();

        y.getProcedureResponses().sort(new Comparator<V1ProcedureResponse>() {
            @Override
            public int compare(V1ProcedureResponse o1, V1ProcedureResponse o2) {
                return o2.getTimeRequested().compareTo(o1.getTimeRequested());
            }
        });

        return y;
    }
}
