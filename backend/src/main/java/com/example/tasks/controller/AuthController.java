package com.example.tasks.controller;

import com.example.tasks.dto.UserDTO;
import com.example.tasks.dto.request.CredentialsDTO;
import com.example.tasks.dto.request.UserRegisterDTO;
import com.example.tasks.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jose4j.lang.JoseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid CredentialsDTO credentialsDTO) throws JoseException {
        String token = authService.login(credentialsDTO);

        return ResponseEntity.ok(Map.of("token", token));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public UserDTO register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        return authService.register(userRegisterDTO);
    }
}
