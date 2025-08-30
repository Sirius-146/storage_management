package com.project.storage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.storage.dto.ProductResponseDTO;
import com.project.storage.handler.NotFoundException;
import com.project.storage.dto.ProductRequestDTO;
import com.project.storage.model.Product;
import com.project.storage.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDTO::fromEntity)
                .toList();
    }

    public ProductResponseDTO findById(Integer id){
        return productRepository.findById(id)
                .map(ProductResponseDTO::fromEntity)
                .orElseThrow(() -> new NotFoundException("Product"));
    }

    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = new Product(dto.name(), dto.brand(), dto.price(), dto.cost(), dto.barcode());

        product = productRepository.save(product);

        return ProductResponseDTO.fromEntity(product);
    }

    public ProductResponseDTO update(Integer id, ProductRequestDTO dto){
        Product productBD = productRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Produto"));
        
        productBD.setName(dto.name());
        productBD.setBrand(dto.brand());
        productBD.setPrice(dto.price());
        productBD.setCost(dto.cost());
        productBD.setBarCode(dto.barcode());

        Product updated = productRepository.save(productBD);

        return ProductResponseDTO.fromEntity(updated);
    }

    public void delete(Integer id){
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }
}
