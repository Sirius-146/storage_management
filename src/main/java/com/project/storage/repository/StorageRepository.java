package com.project.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.storage.model.Location;
import com.project.storage.model.Product;
import com.project.storage.model.Storage;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Integer> {
    Optional<Storage> findByProductAndLocation(Product product, Location location);
}
