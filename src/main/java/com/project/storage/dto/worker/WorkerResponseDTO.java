package com.project.storage.dto.worker;

import com.project.storage.model.Role;
import com.project.storage.model.Worker;

public record WorkerResponseDTO(
    Integer id,
    String name,
    String username,
    Role role
) {
    public static WorkerResponseDTO fromEntity(Worker worker){
        return new WorkerResponseDTO(worker.getId(), worker.getName(), worker.getUsername(), worker.getRole());
    }
}
