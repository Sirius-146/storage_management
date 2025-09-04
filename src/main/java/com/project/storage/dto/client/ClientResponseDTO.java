package com.project.storage.dto.client;

import com.project.storage.model.Client;

public record ClientResponseDTO(
    String name,
    String cpf,
    String phone,
    String email
) {
    public static ClientResponseDTO fromEntity(Client client){
        return new ClientResponseDTO(client.getName(), client.getCpf(), client.getPhone(), client.getEmail());
    }
}
