package com.project.storage.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class ItemSoldId implements Serializable{
    private Integer sell;
    private Integer product;

    public ItemSoldId(){}

    public ItemSoldId(Integer sell, Integer product){
        this.sell = sell;
        this.product = product;
    }
}
