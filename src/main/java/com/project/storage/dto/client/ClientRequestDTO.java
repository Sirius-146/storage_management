package com.project.storage.dto.client;

import com.project.storage.model.Role;

public record ClientRequestDTO(
    String name,
    String cpf,
    String phone,
    String email,
    Role role,
    String address
) {
}
