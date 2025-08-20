package com.project.storage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storage.dto.TransferRequest;
import com.project.storage.repository.LocationRepository;
import com.project.storage.repository.ProductRepository;
import com.project.storage.service.TransferService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransferController {
    
    private final TransferService transferService;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;

    @PostMapping
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        var product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not founf"));
        
        var origin = locationRepository.findById(request.getOriginId())
                .orElseThrow(() -> new IllegalArgumentException("Origin place not founf"));
        
        var destiny = locationRepository.findById(request.getDestinyId())
                .orElseThrow(() -> new IllegalArgumentException("Destiny place not founf"));
        
        transferService.transferProduct(product, origin, destiny, request.getQuantity());

        return ResponseEntity.ok("TransferÊncia concluída com sucesso!");
    }
}
