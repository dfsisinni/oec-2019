package com.project.app.controllers;

import com.project.app.models.entities.ResourceType;
import com.project.app.repositories.ResourceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api/resource_type")
public class V1ResourceTypeController {

    private final ResourceTypeRepository resourceTypeRepository;

    @Autowired
    public V1ResourceTypeController(ResourceTypeRepository resourceTypeRepository) {
        this.resourceTypeRepository = resourceTypeRepository;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void createResourceType(@RequestParam("type") @NotBlank String type) {
        this.resourceTypeRepository.save(ResourceType.builder()
                .type(type).build());
    }

    @RequestMapping(method = RequestMethod.GET, name = "/type")
    public List<ResourceType> getResourceTypes() {
        return this.resourceTypeRepository.findAllBy();
    }
}
