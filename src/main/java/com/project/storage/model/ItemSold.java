package com.project.storage.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"sell", "product"})
@Entity
@Table(name = "items_sold")
public class ItemSold {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private ItemSoldId id;

    @ManyToOne
    @MapsId("sell") // mapeia a PK 'sell' para a FK
    @JoinColumn(name = "id_sell", nullable = false)
    private Sell sell;

    @ManyToOne
    @MapsId("product") // mapeia a PK 'product' para a FK
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "final_value")
    private Double finalValue;

    public ItemSold(Sell sell, Product product, Integer quantity){
        this.sell = sell;
        this.product = product;
        this.quantity = quantity;
        this.id = new ItemSoldId(sell.getId(), product.getId());
        recalculateFinalValue();
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        recalculateFinalValue();
    }

    public void setProduct(Product product) {
        this.product = product;
        recalculateFinalValue();
    }
    
    private void recalculateFinalValue() {
        if (this.product != null && this.quantity != null) {
            this.finalValue = this.quantity * this.product.getPrice();
        }
    }
}
