package com.example.tasks.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskUpdateRequestDTO {
    @NotBlank
    private String taskName;

    @NotNull
    private Long userId;

    @NotBlank
    private String statusTypeId;

    @FutureOrPresent
    private LocalDateTime dueDate;

    @NotBlank
    private String lastUpdatedBy;
}