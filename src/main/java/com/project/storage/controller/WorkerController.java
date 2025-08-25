package com.project.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storage.model.Worker;
import com.project.storage.repository.WorkerRepository;

@RestController
@RequestMapping("/workers")
public class WorkerController {
    @Autowired
    private WorkerRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public Worker register(@RequestBody Worker worker){
        // Criptografar senha antes de salvar
        worker.setPassword(encoder.encode(worker.getPassword()));
        return repository.save(worker);
    }
}
