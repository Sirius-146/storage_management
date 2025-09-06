package com.project.storage.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.storage.dto.client.ClientRequestDTO;
import com.project.storage.dto.client.ClientResponseDTO;
import com.project.storage.handler.NotFoundException;
import com.project.storage.model.Client;
import com.project.storage.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientResponseDTO create(ClientRequestDTO dto){
        Client client = new Client(dto.name(), dto.cpf(), dto.email(), dto.role(), dto.phone(), dto.address());

        clientRepository.save(client);

        return ClientResponseDTO.fromEntity(client);
    }

    public List<ClientResponseDTO> getAllClients(){
        return clientRepository.findAll().stream()
                .map(ClientResponseDTO::fromEntity)
                .toList();
    }

    public Optional<ClientResponseDTO> findById(Integer id){
        return clientRepository.findById(id)
                .map(ClientResponseDTO::fromEntity);
    }

    public Optional<ClientResponseDTO> findByName(String name){
        return clientRepository.findByName(name)
                .map(ClientResponseDTO::fromEntity);
    }

    public ClientResponseDTO update(Integer id, ClientRequestDTO dto){
        Client clientBD = clientRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Client"));

        clientBD.setName(dto.name());
        clientBD.setCpf(dto.cpf());
        clientBD.setPhone(dto.phone());
        clientBD.setEmail(dto.email());
        clientBD.setAddress(dto.address());

        Client updated = clientRepository.save(clientBD);
        
        return ClientResponseDTO.fromEntity(updated);
    }

    public ClientResponseDTO patch(Integer id, ClientRequestDTO dto){
        Client clientBD = clientRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Client"));

        if ( dto.name() != null ) {clientBD.setName(dto.name());}
        if ( dto.cpf() != null ) {clientBD.setCpf(dto.cpf());}
        if ( dto.phone() != null ) {clientBD.setPhone(dto.phone());}
        if ( dto.email() != null ) {clientBD.setEmail(dto.email());}
        if ( dto.address() != null ) {clientBD.setAddress(dto.address());}

        Client updated = clientRepository.save(clientBD);
        
        return ClientResponseDTO.fromEntity(updated);
    }

    public void delete(Integer id){
        if(!clientRepository.existsById(id)){
            throw new NotFoundException("Client");
        }
        clientRepository.deleteById(id);
    }
}
