package com.project.storage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.storage.dto.ProductResponseDTO;
import com.project.storage.dto.ProductRequestDTO;
import com.project.storage.model.Product;
import com.project.storage.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setPrice(dto.getPrice());
        product.setCost(dto.getCost());
        product.setBarCode(dto.getBarcode());

        product = productRepository.save(product);

        return new ProductResponseDTO(
            product.getId(),
            product.getName(),
            product.getBrand(),
            product.getPrice(),
            product.getCost(),
            product.getBarCode()
        );
    }

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(p -> new ProductResponseDTO(
                    p.getId(),
                    p.getName(),
                    p.getBrand(),
                    p.getPrice(),
                    p.getCost(),
                    p.getBarCode()
                ))
                .toList();
    }
}
