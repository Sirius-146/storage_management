package com.project.storage.dto;

public record TransferRequestDTO (
    Integer productId,
    Integer originId,
    Integer destinyId,
    Integer quantity
){}
