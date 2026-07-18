package com.example.tasks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long userId;
// TODO: register auth dto user
    @NotBlank
    private String username;

    @NotBlank
    private String email;
    @NotBlank
    private String password;

    private LocalDate birthDate;
}