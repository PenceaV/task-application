package com.example.tasks.dto.shared;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusTypeSummaryDTO {
    private String statusTypeId;
    private String statusName;
}