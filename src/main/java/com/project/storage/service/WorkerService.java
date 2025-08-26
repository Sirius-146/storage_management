package com.project.storage.service;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.storage.dto.LoginRequest;
import com.project.storage.dto.LoginResponse;
import com.project.storage.dto.WorkerRequest;
import com.project.storage.handler.UnauthorizedException;
import com.project.storage.handler.NotFoundException;
import com.project.storage.model.Worker;
import com.project.storage.repository.WorkerRepository;
import com.project.storage.security.JWTCreator;
import com.project.storage.security.JWTObject;
import com.project.storage.security.JWTProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerService {
    
    private final WorkerRepository workerRepository;
    private final PasswordEncoder encoder;
    private final JWTProperties jwtProperties;

    public Worker register(WorkerRequest request){
        Worker worker = new Worker();
        worker.setUsername(request.username());
        worker.setName(request.name());
        worker.setPassword(encoder.encode(request.password()));
        worker.setDepartment(request.department());
        worker.setRole(request.role());

        return workerRepository.save(worker);
    }

    public LoginResponse authenticate(LoginRequest login){
        Worker worker = workerRepository.findByUsername(login.username())
                .orElseThrow(() -> new NotFoundException("Worker"));

        if (!encoder.matches(login.password(), worker.getPassword())){
            throw new UnauthorizedException("Wrong password");
        }

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60);

        JWTObject jwtObject = new JWTObject(
            worker.getUsername(),
            issuedAt,
            expiration,
            worker.getRole()
        );

        String token = JWTCreator.create(
            jwtProperties.getPrefix(),
            jwtProperties.getKey(),
            jwtObject
        );

        // Retorna login + token
        return new LoginResponse(worker.getName(), worker.getUsername(), token, worker.getRole());
    }

    public Worker searchById(Integer id){
        return workerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Worker"));
    }

    public Worker findByUsername(String username){
        return workerRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Worker %s not found!", username));
    }
}
