package com.example.tasks.service;

import com.example.tasks.domain.User;
import com.example.tasks.dto.UserDTO;
import com.example.tasks.dto.request.CredentialsDTO;
import com.example.tasks.dto.response.UserResponseDTO;
import com.example.tasks.exception.DuplicateEmailException;
import com.example.tasks.exception.DuplicateUsernameException;
import com.example.tasks.exception.InvalidCredentialsException;
import com.example.tasks.exception.UserNotFoundException;
import com.example.tasks.mapper.UserMapper;
import com.example.tasks.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDTO authenticate(CredentialsDTO credentialsDTO) {
        log.info("Authenticating user with email: {}", credentialsDTO.getEmail());
        User user = userRepository.findByEmail(credentialsDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException(
                        "User with email " + credentialsDTO.getEmail() + " not found"));

        if (!credentialsDTO.getPassword().equals(user.getPassword()))
            throw new InvalidCredentialsException("Invalid password");

        return userMapper.toDto(user);
    }

    @Transactional
    public UserDTO register(UserDTO userDTO) {
        log.info("Registering user: {}", userDTO.getUsername());

        User user = userMapper.toEntity(userDTO);

        if (userRepository.existsByUsername(user.getUsername()))
            throw new DuplicateUsernameException("User with username: " + user.getUsername() + " already exists.");
        else if (userRepository.existsByEmail(user.getEmail()))
            throw new DuplicateEmailException("User with email: " + user.getEmail() + " already exists.");

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }
}
