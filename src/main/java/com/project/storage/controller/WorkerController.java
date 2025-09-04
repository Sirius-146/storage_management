package com.project.storage.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storage.dto.worker.WorkerRequestDTO;
import com.project.storage.dto.worker.WorkerResponseDTO;
import com.project.storage.helper.ResponseEntityUtils;
import com.project.storage.service.WorkerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/workers")
@RequiredArgsConstructor
@Tag(name = "Workers", description = "Workers management")
public class WorkerController {
    private final WorkerService workerService;

    @PreAuthorize("hasAnyRole('ADMIN', 'HUMAN_RESOURCES')")
    @Operation(summary = "Create worker", description = "Register a worker in the database", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping("/register")
    public ResponseEntity<WorkerResponseDTO> register(@RequestBody WorkerRequestDTO request) {
        WorkerResponseDTO worker = workerService.register(request);
        URI location = URI.create("/workers/" + worker.id());
        return ResponseEntityUtils.created(location, worker);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HUMAN_RESOURCES')")
    @Operation(summary = "List workers", description = "Return a list with all workers registered", security = { @SecurityRequirement(name = "bearerAuth") })
    @GetMapping()
    public ResponseEntity<List<WorkerResponseDTO>> getAllWorkers(){
        List<WorkerResponseDTO> workers = workerService.getAllWorkers();
        Map<String, String> headers = Map.of("X-Total-Count",String.valueOf(workers.size()));
        return ResponseEntityUtils.fromList(workers, headers);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HUMAN_RESOURCES')")
    @Operation(summary = "List worker", description = "Return a worker by its identifier", security = { @SecurityRequirement(name = "bearerAuth") })
    @GetMapping("/{id}")
    public ResponseEntity<WorkerResponseDTO> findById(@PathVariable Integer id){
        return ResponseEntityUtils.fromOptional(workerService.findById(id));
    }

    // @PreAuthorize("hasAnyRole('ADMIN', 'HUMAN_RESOURCES')")
    @Operation(summary = "Update worker", description = "Update the chosen values of a worker in the database") //, security = { @SecurityRequirement(name = "bearerAuth") }
    @PatchMapping("/{id}")
    public ResponseEntity<WorkerResponseDTO> patch(@PathVariable Integer id, @RequestBody WorkerRequestDTO dto){
        WorkerResponseDTO updated = workerService.patch(id, dto);
        return ResponseEntityUtils.withStatus(HttpStatus.OK, updated);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HUMAN_RESOURCES')")
    @Operation(summary = "Delete worker", description = "Delete a worker from the database", security = { @SecurityRequirement(name = "bearerAuth") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        workerService.delete(id);
        return ResponseEntityUtils.deleted();
    }
}
