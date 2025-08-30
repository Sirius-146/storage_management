package com.project.storage.dto;

import java.time.LocalDateTime;

import com.project.storage.model.MovementType;
import com.project.storage.model.StockMovement;

public record StockMovementResponseDTO (
    Integer id,
    String productName,
    String locationName,
    MovementType type,
    Integer quantity,
    LocalDateTime movementDate
){
    public static StockMovementResponseDTO fromEntity(StockMovement movement){
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
