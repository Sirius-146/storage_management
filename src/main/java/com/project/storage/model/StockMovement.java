package com.project.storage.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movement")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_location", nullable = false)
    private Location location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MovementType type;

    @Column(nullable = false, length = 50)
    private String reason;

    @Column(nullable = false)
    private Integer quantity;

    @Builder.Default
    @Column(name = "movement_date", nullable = false)
    private LocalDateTime movementDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_sell")
    private Sell sell;

    public StockMovement(Product product, Location location, MovementType type, Integer quantity) {
        this.product = product;
        this.location = location;
        this.type = type;
        this.quantity = quantity;
    }
}
