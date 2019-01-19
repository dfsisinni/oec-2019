package com.project.app.models.v1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class V1PatientResponse {

    private String firstName;

    private String lastName;

    private String reasonForVisit;

    private String floor;

    private LocalDateTime lastAssessmentTime;

    private LocalDateTime admissionTime;

    private List<V1ProcedureResponse> procedureResponses;

}
