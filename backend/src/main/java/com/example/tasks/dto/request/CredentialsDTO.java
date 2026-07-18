package com.example.tasks.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialsDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
