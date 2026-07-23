package com.example.tasks.service;

import com.example.tasks.domain.User;
import com.example.tasks.dto.UserDTO;
import com.example.tasks.dto.request.CredentialsDTO;
import com.example.tasks.dto.request.UserRegisterDTO;
import com.example.tasks.exception.DuplicateEmailException;
import com.example.tasks.exception.DuplicateUsernameException;
import com.example.tasks.exception.InvalidCredentialsException;
import com.example.tasks.mapper.UserMapper;
import com.example.tasks.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.util.security.Credential;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Value("${jwt.secret}")
    String jwtSecret;

    @Value("${jwt.expiration.ms}")
    String jwtExpiration;

    public String login(CredentialsDTO credentialsDTO) throws JoseException {
        credentialsDTO.setEmail(new String(Base64.getDecoder().decode(credentialsDTO.getEmail())));
        credentialsDTO.setPassword(new String(Base64.getDecoder().decode(credentialsDTO.getPassword())));

        // hash
        String hashPassword = getHashPassword(credentialsDTO.getPassword());
        User dbUser = userRepository.findByEmail(credentialsDTO.getEmail());

        if (dbUser == null || !hashPassword.equals(dbUser.getPassword()))
            throw new InvalidCredentialsException("Login failed.\nInvalid email or password.");

        String token = createJWToken(credentialsDTO.getEmail());

        if (token == null)
            throw new InvalidCredentialsException("Unauthorized");

        return createJWToken(credentialsDTO.getEmail());
    }

    @Transactional
    public UserDTO register(UserRegisterDTO userRegisterDTO) {

        String hashPassword = getHashPassword(userRegisterDTO.getPassword());
        userRegisterDTO.setPassword(hashPassword);

        User user = userMapper.toEntity(userRegisterDTO);

        if (userRepository.existsByUsername(user.getUsername()))
            throw new DuplicateUsernameException("Username already used!");
        else if (userRepository.existsByEmail(user.getEmail()))
            throw new DuplicateEmailException("Email already used!");

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    private String createJWToken(String email) throws JoseException{
        JwtClaims claims = new JwtClaims();
        claims.setIssuedAtToNow();
        claims.setExpirationTimeMinutesInTheFuture((float) Long.parseLong(jwtExpiration) / (1000 * 60));

        claims.setSubject(email);

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setKey(new AesKey(jwtSecret.getBytes(StandardCharsets.UTF_8)));

        return jws.getCompactSerialization();
    }

    private String getHashPassword(String password) {
        return Credential.MD5.digest(password)
                .replaceFirst("MD5:", "")
                .toLowerCase();
    }
}
