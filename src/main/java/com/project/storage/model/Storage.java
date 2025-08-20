package com.project.storage.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "storages",
    uniqueConstraints = @UniqueConstraint(name="uk_storage_product_location", columnNames={"id_product","id_location"})
)
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"product", "location"})
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_location", nullable = false)
    private Location location;

    @Column(name = "quantity")
    private Integer quantity;

    public Storage(Product product, Location location, Integer quantity){
        this.product = product;
        this.location = location;
        this.quantity = quantity;
    }
}
