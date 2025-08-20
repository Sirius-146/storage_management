package com.project.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.storage.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer>{
    
}
