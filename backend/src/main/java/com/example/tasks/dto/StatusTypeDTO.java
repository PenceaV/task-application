package com.example.tasks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

// DTO pt request response

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusTypeDTO {
    private String statusTypeId;

    @NotBlank
    private String statusName;

    @NotBlank
    private String createdBy;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
    private String lastUpdatedBy;
    private String createdByFullName;
}