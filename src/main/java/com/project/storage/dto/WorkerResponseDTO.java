package com.project.storage.dto;

import com.project.storage.model.Role;
import com.project.storage.model.Worker;

public record WorkerResponseDTO(
    Integer id,
    String username,
    Role role
) {
    public static WorkerResponseDTO fromEntity(Worker worker){
        return new WorkerResponseDTO(worker.getId(), worker.getUsername(), worker.getRole());
    }
}
