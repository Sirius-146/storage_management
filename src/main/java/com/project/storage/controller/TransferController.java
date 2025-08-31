package com.project.storage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storage.dto.TransferRequestDTO;
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
    public ResponseEntity<String> transfer(@RequestBody TransferRequestDTO request) {
        var product = productRepository.findById(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not founf"));
        
        var origin = locationRepository.findById(request.originId())
                .orElseThrow(() -> new IllegalArgumentException("Origin place not founf"));
        
        var destiny = locationRepository.findById(request.destinyId())
                .orElseThrow(() -> new IllegalArgumentException("Destiny place not founf"));
        
        transferService.transferProduct(product, origin, destiny, request.quantity());

        return ResponseEntity.ok("TransferÊncia concluída com sucesso!");
    }
}
