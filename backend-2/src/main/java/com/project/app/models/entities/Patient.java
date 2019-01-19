package com.project.app.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Column(name = "reason_for_vist")
    private String reasonForVisit;

    @NotNull
    private LocalDateTime admissionTime;

    @NotNull
    private LocalDateTime lastAssessmentTime;

    @NotNull
    private boolean active;

    @NotNull
    @ManyToMany
    @JoinTable(name = "patient_resource_assignment",
        joinColumns = @JoinColumn(name = "resource_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"))
    private List<Resource> assignedResources;

    @NotNull
    @OneToMany(mappedBy = "patient")
    private List<ResourceRequest> resourceRequests;

    @NotNull
    private String floor;
}
