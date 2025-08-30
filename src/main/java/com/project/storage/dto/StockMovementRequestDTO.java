package com.project.storage.dto;

import com.project.storage.model.MovementType;

public record StockMovementRequestDTO(
    Integer productId,
    Integer locationId,
    MovementType type,
    Integer quantity
) {
}