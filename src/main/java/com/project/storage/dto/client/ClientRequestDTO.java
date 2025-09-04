package com.project.storage.dto.client;

public record ClientRequestDTO(
    String name,
    String cpf,
    String phone,
    String email,
    String address
) {
}
