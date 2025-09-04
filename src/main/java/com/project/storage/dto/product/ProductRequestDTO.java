package com.project.storage.dto.product;

public record ProductRequestDTO (
    String name,
    String brand,
    Double price,
    Double cost,
    String barcode
){}
