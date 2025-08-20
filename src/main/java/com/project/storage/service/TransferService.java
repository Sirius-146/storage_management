package com.project.storage.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.project.storage.model.Location;
import com.project.storage.model.MovementType;
import com.project.storage.model.Product;
import com.project.storage.model.StockMovement;
import com.project.storage.model.Storage;
import com.project.storage.repository.StockMovementRepository;
import com.project.storage.repository.StorageRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final StorageRepository storageRepository;
    private final StockMovementRepository stockMovementRepository;

    @Transactional
    public void transferProduct(Product product, Location origin, Location destiny, Integer quantity){
        if (quantity <= 0){
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        // Estoque de origem
        Storage originStorage = storageRepository.findByProductAndLocation(product, origin)
                .orElseThrow(() -> new IllegalStateException(
                    "Product not found in origin storage: " + origin.getName()
                ));
        
        if (originStorage.getQuantity() < quantity){
            throw new IllegalStateException("Storage not enough in: " + origin.getName());
        }

        // Estoque de destino (cria caso não exista ainda)
        Storage destinyStorage = storageRepository.findByProductAndLocation(product, destiny)
                .orElseGet(() -> storageRepository.save(new Storage(product, destiny, 0)));

        // Atualiza
        originStorage.setQuantity(originStorage.getQuantity() - quantity);
        destinyStorage.setQuantity(destinyStorage.getQuantity() + quantity);

        storageRepository.save(originStorage);
        storageRepository.save(destinyStorage);

        // Registra movimentos
        StockMovement out = StockMovement.builder()
            .product(product)
            .location(origin)
            .type(MovementType.OUT)
            .reason("Transferência para " + destiny.getName())
            .quantity(quantity)
            .movementDate(LocalDateTime.now())
            .build();

        StockMovement in = StockMovement.builder()
            .product(product)
            .location(destiny)
            .type(MovementType.IN)
            .reason("Transferência de " + origin.getName())
            .quantity(quantity)
            .movementDate(LocalDateTime.now())
            .build();
        
        stockMovementRepository.save(out);
        stockMovementRepository.save(in);
    }
}
