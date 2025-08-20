package com.project.storage.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storage.dto.StockMovementRequestDTO;
import com.project.storage.dto.StockMovementResponseDTO;
import com.project.storage.service.StockMovementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {
    private final StockMovementService stockMovementService;

    @GetMapping
    public List<StockMovementResponseDTO> getAllMovements() {
        return stockMovementService.getAllMovements();
    }

    @PostMapping
    public StockMovementResponseDTO creaMovement(@RequestBody StockMovementRequestDTO dto) {
        return stockMovementService.createMovement(dto);
    }
}
