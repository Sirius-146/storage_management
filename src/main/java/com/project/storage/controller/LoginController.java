package com.project.storage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storage.dto.login.LoginRequestDTO;
import com.project.storage.dto.login.LoginResponseDTO;
import com.project.storage.service.WorkerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final WorkerService workerService;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> logar(@RequestBody LoginRequestDTO login){
        LoginResponseDTO response = workerService.authenticate(login);
        return ResponseEntity.ok(response);
    }
}
