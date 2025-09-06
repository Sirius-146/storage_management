package com.project.storage.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.storage.dto.worker.WorkerRequestDTO;
import com.project.storage.dto.worker.WorkerResponseDTO;
import com.project.storage.handler.NotFoundException;
import com.project.storage.model.Worker;
import com.project.storage.repository.WorkerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerService {
    
    private final WorkerRepository workerRepository;
    private final PasswordEncoder encoder;

    public WorkerResponseDTO create(WorkerRequestDTO dto){
        Worker worker = new Worker(
            dto.name(), dto.username(), encoder.encode(dto.password()),
            dto.cpf(), dto.email(), dto.role(), dto.department()
        );

        workerRepository.save(worker);

        return WorkerResponseDTO.fromEntity(worker);
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

    @Transactional
    public WorkerResponseDTO update(Integer id, WorkerRequestDTO dto){
        Worker workerBD = workerRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Product"));
        
        workerBD.setName(dto.name());
        workerBD.setUsername(dto.username());
        workerBD.setPassword(encoder.encode(dto.password()));
        workerBD.setDepartment(dto.department());
        workerBD.setRole(dto.role());

        return WorkerResponseDTO.fromEntity(workerBD);
    }

    @Transactional
    public WorkerResponseDTO patch(Integer id, WorkerRequestDTO dto){
        Worker workerBD = workerRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Product"));
        
        if ( dto.name() != null ) {workerBD.setName(dto.name());}
        if ( dto.username() != null ) {workerBD.setUsername(dto.username());}
        if ( dto.password() != null ) {workerBD.setPassword(encoder.encode(dto.password()));}
        if ( dto.department() != null ) {workerBD.setDepartment(dto.department());}
        if ( dto.role() != null ) {workerBD.setRole(dto.role());}

        return WorkerResponseDTO.fromEntity(workerBD);
    }

    public void delete (Integer id){
        if(!workerRepository.existsById(id)){
            throw new NotFoundException("Worker");
        }
        workerRepository.deleteById(id);
    }
}
