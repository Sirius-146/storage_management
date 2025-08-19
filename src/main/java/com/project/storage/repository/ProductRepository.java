package com.project.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.storage.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    List<Product> findByNameContaining(String name);

    Product findByProductName(String name);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    List<Product> filtrarPorNome(@Param("name") String name);

}
