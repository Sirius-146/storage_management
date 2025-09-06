package com.project.storage.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.storage.dto.login.LoginRequestDTO;
import com.project.storage.dto.login.LoginResponseDTO;
import com.project.storage.handler.NotFoundException;
import com.project.storage.handler.UnauthorizedException;
import com.project.storage.model.Role;
import com.project.storage.model.User;
import com.project.storage.repository.ClientRepository;
import com.project.storage.repository.WorkerRepository;
import com.project.storage.security.JWTCreator;
import com.project.storage.security.JWTObject;
import com.project.storage.security.JWTProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final WorkerRepository workerRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;
    private final JWTProperties jwtProperties;

    public LoginResponseDTO authenticate(LoginRequestDTO login){
        Optional<? extends User> userOpt = workerRepository.findByUsername(login.username())
                .map(u -> (User) u)
                .or(() -> clientRepository.findByUsername(login.username()).map(u -> (User) u));

        User user = userOpt.orElseThrow(() ->
            new NotFoundException("User"));

        validatePassword(login.password(), user.getPassword());

        // Retorna login + token
        return buildToken(user.getName(), user.getUsername(), user.getRole());
    }

    private void validatePassword(String raw, String encoded){
        if (!encoder.matches(raw, encoded)){
            throw new UnauthorizedException("Invalid password");
        }
    }

    private LoginResponseDTO buildToken(String name, String username, Role role){
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60);

        JWTObject jwtObject = new JWTObject( username, issuedAt, expiration, role );

        String token = JWTCreator.create(
            jwtProperties.getPrefix(),
            jwtProperties.getKey(),
            jwtObject
        );

        return new LoginResponseDTO(name, username, token, role);
    }
}