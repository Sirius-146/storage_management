package com.project.storage.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private Integer productId;
    private Integer originId;
    private Integer destinyId;
    private Integer quantity;
}
