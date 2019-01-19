package com.project.app.models.v1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class V1ProcedureResponse {


    private String procedureName;

    private LocalDateTime timeRequested;

    private String status;

    private String doctor;

    private String notes;

}
