package com.project.storage.dto;

import com.project.storage.model.Role;

public record LoginResponseDTO(
    String name,
    String username,
    String token,
    Role role
) {}