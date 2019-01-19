package com.project.app.repositories;

import com.project.app.models.entities.Resource;
import com.project.app.models.entities.ResourceType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResourceRepository extends CrudRepository<Resource, Long> {

    List<Resource> getResourcesByType(ResourceType type);

}
