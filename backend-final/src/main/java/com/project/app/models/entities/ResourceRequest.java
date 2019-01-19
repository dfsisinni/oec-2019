package com.project.app.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Table(name = "resource_request")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String procedureName;

    @NotNull
    private LocalDateTime timeRequested;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @NotNull /*pending, completed*/
    private String state;

    @NotNull
    private int rating;

    @NotNull
    private boolean override;

    @NotNull
    private String notes;

}
