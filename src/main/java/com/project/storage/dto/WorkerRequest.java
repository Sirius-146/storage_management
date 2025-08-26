package com.project.storage.dto;

import com.project.storage.model.Role;

public record WorkerRequest (
    String name,
    String username,
    String password,
    String department,
    Role role
){}
