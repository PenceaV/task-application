package com.example.tasks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long userId;

    @NotBlank
    private String username;

    private LocalDateTime birthDate;
    private Boolean isInternal;

    @NotBlank
    private String createdBy;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
    private String lastUpdatedBy;
    private String createdByFullName;
}