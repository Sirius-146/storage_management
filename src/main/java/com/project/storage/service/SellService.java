package com.project.storage.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.project.storage.model.ItemSold;
import com.project.storage.model.MovementType;
import com.project.storage.model.Sell;
import com.project.storage.model.StockMovement;
import com.project.storage.model.Storage;
import com.project.storage.repository.SellRepository;
import com.project.storage.repository.StockMovementRepository;
import com.project.storage.repository.StorageRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SellService {

    private final SellRepository sellRepository;
    private final StorageRepository storageRepository;
    private final StockMovementRepository stockMovementRepository;

    @Transactional
    public Sell registrarVenda(Sell sell) {
        // 1. Salvar a venda
        Sell savedSell = sellRepository.save(sell);

        // 2. Processar itens vendidos
        for (ItemSold item : savedSell.getItems()) {

            // 2.1 Atualizar estoque
            Storage storage = storageRepository.findByProductAndLocation(
                    item.getProduct(), savedSell.getLocation()
            ).orElseThrow(() -> new IllegalStateException(
                "Estoque não encontrado para produto=" + item.getProduct().getName() +
                " em location=" + savedSell.getLocation().getName()
            ));

            if (storage.getQuantity() < item.getQuantity()) {
                throw new IllegalStateException("Estoque insuficiente para produto=" + item.getProduct().getName());
            }

            storage.setQuantity(storage.getQuantity() - item.getQuantity());
            storageRepository.save(storage);

            // 3. Registrar movimentação
            StockMovement movement = StockMovement.builder()
                    .product(item.getProduct())
                    .location(savedSell.getLocation())
                    .type(MovementType.OUT)
                    .reason("Venda")
                    .quantity(item.getQuantity())
                    .movementDate(LocalDateTime.now())
                    .sell(savedSell)
                    .build();

            stockMovementRepository.save(movement);
        }

        savedSell.recalculateTotal();
        return savedSell;
    }
}

