package com.project.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.storage.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{   
}
