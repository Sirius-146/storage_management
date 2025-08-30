package com.project.storage.dto;

public record TransferRequest (
    Integer productId,
    Integer originId,
    Integer destinyId,
    Integer quantity
){}
