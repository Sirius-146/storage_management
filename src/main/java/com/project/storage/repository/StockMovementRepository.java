package com.project.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.storage.model.StockMovement;

public interface StockMovementRepository extends JpaRepository<StockMovement, Integer> {}
