package com.project.storage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storage.dto.WorkerRequest;
import com.project.storage.dto.WorkerResponseDTO;
import com.project.storage.model.Worker;
import com.project.storage.service.WorkerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/workers")
@RequiredArgsConstructor
public class WorkerController {
    
    private final WorkerService workerService;

    @PostMapping("/register")
    public ResponseEntity<WorkerResponseDTO> register(@RequestBody WorkerRequest request) {
        Worker worker = workerService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(WorkerResponseDTO.fromEntity(worker));
    }
}
