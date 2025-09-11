package com.project.storage.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storage.dto.client.ClientRequestDTO;
import com.project.storage.dto.client.ClientResponseDTO;
import com.project.storage.helper.ResponseEntityUtils;
import com.project.storage.service.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Clients management")
public class ClientController {
    private final ClientService clientService;
    
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Create client", description = "Register a client in the database", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping("/register")
    public ResponseEntity<ClientResponseDTO> create(@RequestBody ClientRequestDTO request){
        ClientResponseDTO client = clientService.create(request);
        URI location = URI.create("/clients" + client.id());
        return ResponseEntityUtils.created(location, client);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "List clients", description = "Return a list with all clients registered", security = { @SecurityRequirement(name = "bearerAuth") })
    @GetMapping()
    public ResponseEntity<List<ClientResponseDTO>> getAllClients(){
        List<ClientResponseDTO> clients = clientService.getAllClients();
        Map<String, String> headers = Map.of("X-Total-Count", String.valueOf(clients.size()));
        return ResponseEntityUtils.fromList(clients, headers);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "List client", description = "Return a clients by its identifier", security = { @SecurityRequirement(name = "bearerAuth") })
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable Integer id){
        return ResponseEntityUtils.fromOptional(clientService.findById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Update client", description = "Update the chosen values of a client in the database", security = { @SecurityRequirement(name = "bearerAuth") })
    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> patch(@PathVariable Integer id, @RequestBody ClientRequestDTO dto){
        ClientResponseDTO updated = clientService.patch(id, dto);
        return ResponseEntityUtils.withStatus(HttpStatus.OK, updated);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Delete client", description = "Delete a client from the database", security = { @SecurityRequirement(name = "bearerAuth") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        clientService.delete(id);
        return ResponseEntityUtils.deleted();
    }
}
