package com.example.tasks.mapper;

import com.example.tasks.domain.User;
import com.example.tasks.dto.UserDTO;
import com.example.tasks.dto.request.UserRegisterDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDto(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .build();
    }

    public User toEntity(UserRegisterDTO registerDTO) {
        return User.builder()
                .username(registerDTO.getUsername())
                .email(registerDTO.getEmail())
                .password(registerDTO.getPassword())
                .birthDate(registerDTO.getBirthDate())
                .build();
    }
}