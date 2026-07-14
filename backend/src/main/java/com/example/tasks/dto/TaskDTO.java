package com.example.tasks.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {
    private long taskId;

    @NotBlank
    private String taskName;

    @NotNull
    private Long userId;

    @NotBlank
    private String statusTypeId;

    @FutureOrPresent
    private LocalDateTime dueDate;

    @NotBlank
    private String createdBy;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
    private String lastUpdatedBy;
    private String createdByFullName;
}
