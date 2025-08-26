package com.project.storage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.storage.dto.StockMovementRequestDTO;
import com.project.storage.dto.StockMovementResponseDTO;
import com.project.storage.handler.NotFoundException;
import com.project.storage.model.StockMovement;
import com.project.storage.repository.LocationRepository;
import com.project.storage.repository.ProductRepository;
import com.project.storage.repository.StockMovementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;

    public List<StockMovementResponseDTO> getAllMovements() {
        return stockMovementRepository.findAll()
                .stream()
                .map(m -> new StockMovementResponseDTO(
                    m.getId(),
                    m.getProduct().getName(),
                    m.getLocation().getName(),
                    m.getType(),
                    m.getQuantity(),
                    m.getMovementDate()
                ))
                .collect(Collectors.toList());
    }

    public StockMovementResponseDTO createMovement(StockMovementRequestDTO dto) {
        var product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new NotFoundException("Product"));
        var location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new NotFoundException("Local"));
        
        var movement = new StockMovement(
            product,
            location,
            dto.getType(),
            dto.getQuantity()
        );

        stockMovementRepository.save(movement);

        return new StockMovementResponseDTO(
            movement.getId(),
            movement.getProduct().getName(),
            movement.getLocation().getName(),
            movement.getType(),
            movement.getQuantity(),
            movement.getMovementDate()
        );

    }
}
