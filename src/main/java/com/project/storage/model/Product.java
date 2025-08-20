package com.project.storage.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data // Gera getters, setters, equals, hashCode e toString
@NoArgsConstructor // Construtor padrão (obrigatório pro JPA)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name= "name",length = 50, nullable = false)
    private String name;

    @Column(name = "brand", length = 50)
    private String brand;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "bar_code")
    private String barCode;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Storage> storages = new ArrayList<>();

    public Product(String name, String brand, Double price, String barCode) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.barCode = barCode;
    }
}
