package com.example.tasks.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRegisterDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotNull
    private LocalDate birthDate;
}
