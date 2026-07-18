package com.example.tasks.controller;

import com.example.tasks.dto.UserDTO;
import com.example.tasks.dto.request.CredentialsDTO;
import com.example.tasks.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/authenticate")
    public UserDTO authenticate(@RequestBody @Valid CredentialsDTO credentialsDTO) {
        return authService.authenticate(credentialsDTO);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@RequestBody @Valid UserDTO userDTO) {
        return authService.register(userDTO);
    }
}
