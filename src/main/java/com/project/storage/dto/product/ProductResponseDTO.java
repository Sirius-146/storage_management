package com.project.storage.dto.product;

import com.project.storage.model.Product;

public record ProductResponseDTO (
    Integer id,
    String name,
    String brand,
    Double price,
    String barcode
){
    public static ProductResponseDTO fromEntity(Product product){
        return new ProductResponseDTO(
            product.getId(),
            product.getName(),
            product.getBrand(),
            product.getPrice(),
            product.getBarCode());
    }
}
