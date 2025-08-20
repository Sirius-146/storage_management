package com.project.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.storage.model.ItemSold;
import com.project.storage.model.ItemSoldId;

public interface ItemSoldRepository extends JpaRepository<ItemSold, ItemSoldId> {}
