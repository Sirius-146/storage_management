package com.project.storage.dto;

public record ProductRequestDTO (
    String name,
    String brand,
    Double price,
    Double cost,
    String barcode
){}
