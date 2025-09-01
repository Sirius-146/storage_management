package com.project.storage.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storage.dto.WorkerRequestDTO;
import com.project.storage.dto.WorkerResponseDTO;
import com.project.storage.helper.ResponseEntityUtils;
import com.project.storage.service.WorkerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/workers")
@RequiredArgsConstructor
public class WorkerController {
    
    private final WorkerService workerService;

    @PostMapping("/register")
    public ResponseEntity<WorkerResponseDTO> register(@RequestBody WorkerRequestDTO request) {
        WorkerResponseDTO worker = workerService.register(request);
        URI location = URI.create("/workers/" + worker.id());
        return ResponseEntityUtils.created(location, worker);
    }

    @GetMapping()
    public ResponseEntity<List<WorkerResponseDTO>> getAllWorkers(){
        List<WorkerResponseDTO> workers = workerService.getAllWorkers();
        Map<String, String> headers = Map.of("X-Total-Count",String.valueOf(workers.size()));
        return ResponseEntityUtils.fromList(workers, headers);
    }
}
