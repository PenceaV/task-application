package com.example.tasks.dto.response;

import com.example.tasks.dto.shared.StatusTypeSummaryDTO;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseDTO {
    private long taskId;
    private String taskName;
    private Long userId;
    private StatusTypeSummaryDTO statusType;
    private LocalDateTime dueDate;
    private String createdBy;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
    private String lastUpdatedBy;
    private String createdByFullName;
}