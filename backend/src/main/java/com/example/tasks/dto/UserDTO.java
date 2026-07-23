package com.example.tasks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long userId;

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    private LocalDate birthDate;
}