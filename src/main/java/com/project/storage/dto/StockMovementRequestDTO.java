package com.project.storage.dto;

import com.project.storage.model.MovementType;

import lombok.Data;

@Data
public class StockMovementRequestDTO {
    private Integer productId;
    private Integer locationId;
    private MovementType type;
    private Integer quantity;
}
