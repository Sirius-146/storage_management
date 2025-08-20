package com.project.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.storage.model.Sell;

public interface SellRepository extends JpaRepository<Sell, Integer> {}
