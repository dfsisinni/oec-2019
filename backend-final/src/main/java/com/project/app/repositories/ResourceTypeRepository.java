package com.project.app.repositories;

import com.project.app.models.entities.ResourceType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResourceTypeRepository extends CrudRepository<ResourceType, String> {

    List<ResourceType> findAllBy();

}
