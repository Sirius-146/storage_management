package com.project.storage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.storage.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer>{
    Optional<Client> findByName(String name);
}