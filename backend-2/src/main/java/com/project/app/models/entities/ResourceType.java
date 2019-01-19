package com.project.app.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Table(name = "resource_type")
@Entity
@Data
@Builder
@NotNull
@NoArgsConstructor
@AllArgsConstructor
public class ResourceType {

    @Id
    @NotBlank
    private String type;

}
