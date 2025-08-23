package com.project.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.storage.model.Apartment;

public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {
   
}