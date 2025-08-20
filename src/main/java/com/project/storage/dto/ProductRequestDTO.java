package com.project.storage.dto;

import lombok.Data;

@Data
public class ProductRequestDTO {
    private String name;
    private String brand;
    private Double price;
    private String barcode;
}
