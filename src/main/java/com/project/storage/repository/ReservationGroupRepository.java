package com.project.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.storage.model.ReservationGroup;

public interface ReservationGroupRepository extends JpaRepository<ReservationGroup, Integer>{
    
}
