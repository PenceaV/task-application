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

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        log.info("Users retrieved!");

        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDTO getUserById(Long id) {
        log.info("Retrieving user with id: {}", id);

        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public UserDTO getUserByUsername(String username) {
        log.info("Retrieving user with username: {}", username);

        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        log.info("Updating user with id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id));

        user.setUsername(userDTO.getUsername());
        user.setBirthDate(userDTO.getBirthDate());
        user.setLastUpdateDate(LocalDateTime.now());

        User updatedUser = userRepository.save(user);

        return userMapper.toDto(updatedUser);
    }

    @Transactional
    public UserDTO deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id));

        UserDTO deletedUser = userMapper.toDto(user);

        userRepository.delete(user);

        return deletedUser;
    }
}
