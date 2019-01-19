package com.project.app.controllers;

import com.project.app.models.entities.Resource;
import com.project.app.models.entities.ResourceRequest;
import com.project.app.models.entities.ResourceType;
import com.project.app.models.v1.request.V1ResourceRequest;
import com.project.app.models.v1.response.V1ResourceRequestResponse;
import com.project.app.models.v1.response.V1Response;
import com.project.app.repositories.ResourceRepository;
import com.project.app.repositories.ResourceRequestRepository;
import com.project.app.repositories.ResourceTypeRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/resource")
public class V1ResourceController {

    private final ResourceTypeRepository resourceTypeRepository;
    private final ResourceRepository resourceRepository;

    private final ResourceRequestRepository resourceRequestRepository;

    @Autowired
    public V1ResourceController(ResourceTypeRepository resourceTypeRepository, ResourceRepository resourceRepository, ResourceRequestRepository resourceRequestRepository) {
        this.resourceTypeRepository = resourceTypeRepository;
        this.resourceRepository = resourceRepository;
        this.resourceRequestRepository = resourceRequestRepository;
    }


    @RequestMapping(method = RequestMethod.PUT, name = "/create")
    public void createResource(@NotNull @Valid V1ResourceRequest request) {
        val resource = Resource.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .type(resourceTypeRepository.findById(request.getType()).get())
                .build();

        this.resourceRepository.save(resource);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public V1Response<List<V1ResourceRequestResponse>> getResourceRequests(@NotNull @PathVariable("id") Long id) {
        val resource = this.resourceRepository.findById(id).get();
        val resourceRequest = this.resourceRequestRepository.findResourceRequestByResource(resource)
                .stream().filter(x -> x.getState().equals("pending")).collect(Collectors.toList());
        resourceRequest.sort(new Comparator<ResourceRequest>() {
            @Override
            public int compare(ResourceRequest o1, ResourceRequest o2) {
                return Double.compare(calculateRating(o2), calculateRating(o1));
            }
        });


        return V1Response.of(resourceRequest.stream()
                .map(r -> V1ResourceRequestResponse.builder()
                    .floor(r.getPatient().getFloor())
                    .patientId(r.getPatient().getId())
                    .patientName(r.getPatient().getFirstName() + " " + r.getPatient().getLastName())
                    .procedureName(r.getProcedureName())
                    .timeRequested(r.getTimeRequested())
                    .build())
                .collect(Collectors.toList()));
    }

    private double calculateRating(ResourceRequest rq) {
        int o1Floor = 0;
        if (rq.getPatient().getFloor().equals("Regular")) {
            o1Floor = 1;
        } else if (rq.getPatient().getFloor().equals("Step-Down")) {
            o1Floor = 2;
        } else {
            o1Floor = 3;
        }
        int o1Rating = rq.getRating();
        val o1Request = rq.getPatient().getResourceRequests().stream()
                .filter(y -> y.getState().equals("completed"))
                .collect(Collectors.toList());
        o1Request.sort(new Comparator<ResourceRequest>() {
            @Override
            public int compare(ResourceRequest o1, ResourceRequest o2) {
                return o2.getTimeRequested().compareTo(o1.getTimeRequested());
            }
        });

        int days = 1;
        if (o1Request.size() > 0) {
            days = (int) Duration.between(LocalDateTime.now(), o1Request.get(o1Request.size() - 1).getTimeRequested()).toDays();
        }
        if (days == 0) {
            days = 1;
        }

        val finalVal = o1Floor*Math.pow(10, o1Rating)*days;

        System.out.println(rq.getPatient().getFirstName() + " " + finalVal);
        System.out.println(o1Floor);
        System.out.println(o1Rating);
        System.out.println(days);
        return finalVal;
    }

}
