package com.project.storage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.storage.dto.stock.StockMovementRequestDTO;
import com.project.storage.dto.stock.StockMovementResponseDTO;
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
                .map(StockMovementResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public StockMovementResponseDTO createMovement(StockMovementRequestDTO dto) {
        var product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new NotFoundException("Product"));
        var location = locationRepository.findById(dto.locationId())
                .orElseThrow(() -> new NotFoundException("Local"));
        
        var movement = new StockMovement(
            product,
            location,
            dto.type(),
            dto.quantity()
        );

        stockMovementRepository.save(movement);

        return StockMovementResponseDTO.fromEntity(movement);

    }
}
