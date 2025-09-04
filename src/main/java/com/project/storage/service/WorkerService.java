package com.project.storage.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.storage.dto.login.LoginRequestDTO;
import com.project.storage.dto.login.LoginResponseDTO;
import com.project.storage.dto.worker.WorkerRequestDTO;
import com.project.storage.dto.worker.WorkerResponseDTO;
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

    public WorkerResponseDTO create(WorkerRequestDTO dto){
        Worker worker = new Worker(
            dto.name(), dto.username(), encoder.encode(dto.password()),
            dto.role(), dto.department()
        );

        workerRepository.save(worker);

        return WorkerResponseDTO.fromEntity(worker);
    }

    public LoginResponseDTO authenticate(LoginRequestDTO login){
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
        return new LoginResponseDTO(worker.getName(), worker.getUsername(), token, worker.getRole());
    }

    public List<WorkerResponseDTO> getAllWorkers() {
        return workerRepository.findAll().stream()
                .map(WorkerResponseDTO::fromEntity)
                .toList();
    }

    public Optional<WorkerResponseDTO> findById(Integer id){
        return workerRepository.findById(id)
                .map(WorkerResponseDTO::fromEntity);
    }

    public Optional<WorkerResponseDTO> findByUsername(String username){
        return workerRepository.findByUsername(username)
                .map(WorkerResponseDTO::fromEntity);
    }

    public WorkerResponseDTO update(Integer id, WorkerRequestDTO dto){
        Worker workerBD = workerRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Product"));
        
        workerBD.setName(dto.name());
        workerBD.setUsername(dto.username());
        workerBD.setPassword(encoder.encode(dto.password()));
        workerBD.setDepartment(dto.department());
        workerBD.setRole(dto.role());

        Worker updated = workerRepository.save(workerBD);

        return WorkerResponseDTO.fromEntity(updated);
    }

    public WorkerResponseDTO patch(Integer id, WorkerRequestDTO dto){
        Worker workerBD = workerRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Product"));
        
        if ( dto.name() != null ) {workerBD.setName(dto.name());}
        if ( dto.username() != null ) {workerBD.setUsername(dto.username());}
        if ( dto.password() != null ) {workerBD.setPassword(encoder.encode(dto.password()));}
        if ( dto.department() != null ) {workerBD.setDepartment(dto.department());}
        if ( dto.role() != null ) {workerBD.setRole(dto.role());}

        Worker updated = workerRepository.save(workerBD);

        return WorkerResponseDTO.fromEntity(updated);
    }

    public void delete (Integer id){
        if(!workerRepository.existsById(id)){
            throw new NotFoundException("Worker");
        }
        workerRepository.deleteById(id);
    }
}
