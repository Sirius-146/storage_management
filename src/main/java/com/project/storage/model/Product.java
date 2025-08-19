package com.project.storage.model;

import jakarta.persistence.*;

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Integer id;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer quantity;
    @Column(length = 50, nullable = false)
    private String brand;
    @Column(nullable = false)
    private Double value;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Integer getQuantity() {return quantity;}
    public void setQuantity(Integer quantity) {this.quantity = quantity;}
    public String getBrand() {return brand;}
    public void setBrand(String brand) {this.brand = brand;}
    public Double getValue() {return value;}
    public void setValue(Double value) {this.value = value;}
}
