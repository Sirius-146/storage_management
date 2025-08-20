package com.project.storage.dto;

import java.time.LocalDateTime;

import com.project.storage.model.MovementType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockMovementResponseDTO {
    private Integer id;
    private String productName;
    private String locationName;
    private MovementType type;
    private Integer quantity;
    private LocalDateTime movementDate;
}
