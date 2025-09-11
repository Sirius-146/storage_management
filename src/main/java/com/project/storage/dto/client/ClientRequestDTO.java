package com.project.storage.dto.client;

import com.project.storage.model.Role;

public record ClientRequestDTO(
    String name,
    String username,
    String password,
    String cpf,
    String phone,
    String email,
    String address,
    Role role
) {
}
