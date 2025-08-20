package com.project.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseDTO {
    private Integer id;
    private String name;
    private String brand;
    private Double price;
    private String barcode;
}
