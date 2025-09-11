package com.project.storage.dto.client;

import com.project.storage.model.Client;
import com.project.storage.model.Role;

public record ClientResponseDTO(
    Integer id,
    String name,
    String username,
    String cpf,
    String phone,
    String email,
    Role role
) {
    public static ClientResponseDTO fromEntity(Client client){
        return new ClientResponseDTO(client.getId(), client.getName(), client.getUsername(), client.getCpf(), client.getPhone(), client.getEmail(), client.getRole());
    }
}
