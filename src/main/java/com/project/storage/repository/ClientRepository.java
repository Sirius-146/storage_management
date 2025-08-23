package com.project.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.storage.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer>{
    
}