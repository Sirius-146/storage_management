package com.project.storage.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storage.dto.LoginRequest;
import com.project.storage.dto.LoginResponse;
import com.project.storage.handler.UnauthorizedException;
import com.project.storage.model.Worker;
import com.project.storage.repository.WorkerRepository;
import com.project.storage.security.JWTCreator;
import com.project.storage.security.JWTObject;
import com.project.storage.security.JWTProperties;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JWTProperties jwtProperties;
    
    @Autowired
    private WorkerRepository repository;

    @PostMapping
    public LoginResponse logar(@RequestBody LoginRequest login){
        Worker worker = repository.findByUsername((login.getUsername()));
        
        if (worker == null) {
            //throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "msg");
            throw new UnauthorizedException("Usuário não encontrado: " + login.getUsername());
        }
        boolean passwordOk = encoder.matches(login.getPassword(), worker.getPassword());
        if(!passwordOk){
            //throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "msg");
            throw new UnauthorizedException("Senha inválida para o login: " + login.getUsername());
        }
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60);

        JWTObject jwtObject = new JWTObject(
            worker.getUsername(),
            issuedAt,
            expiration,
            worker.getRole()
        );

        String token = JWTCreator.create(
            jwtProperties.getPrefix(),
            jwtProperties.getKey(),
            jwtObject
        );
        // Retorna login + token
        LoginResponse sessao = new LoginResponse();
        sessao.setLogin(worker.getUsername());
        sessao.setToken(token);
        
        return sessao;
    }
}
