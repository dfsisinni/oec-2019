package com.project.app.models.v1.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class V1ProcedureRequest {

    private String procedureName;

    private String resource;

    private Long patientId;

    private Long rating;

    private String notes;

}
