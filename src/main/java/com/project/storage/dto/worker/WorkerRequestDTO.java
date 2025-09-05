package com.project.storage.dto.worker;

import com.project.storage.model.Role;

public record WorkerRequestDTO (
    String name,
    String username,
    String password,
    String cpf,
    String email,
    String department,
    Role role
){}
