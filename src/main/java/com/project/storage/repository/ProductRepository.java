package com.project.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.storage.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
