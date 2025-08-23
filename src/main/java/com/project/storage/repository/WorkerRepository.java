package com.project.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.storage.model.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Integer>{
    
}
