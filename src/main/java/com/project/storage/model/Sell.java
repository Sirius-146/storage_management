package com.project.storage.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name= "sells")
public class Sell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "id_worker", nullable = false)
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "id_reservation", nullable = false)
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "id_location")
    private Location location;

    @OneToMany(mappedBy = "sell", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemSold> items = new ArrayList<>();

    @Column(name = "total_value")
    private Double totalValue = 0.0;
    
    public Sell(Date date, Worker worker, Reservation reservation, Location location) {
        this.date = date;
        this.worker = worker;
        this.reservation = reservation;
        this.location = location;
    }

    public void addItem(ItemSold item){
        items.add(item);
        item.setSell(this);
        recalculateTotal();
    }

    public void removeItem(ItemSold item){
        items.remove(item);
        recalculateTotal();
    }

    public void recalculateTotal(){
        this.totalValue = items.stream()
                                .mapToDouble(ItemSold::getFinalValue)
                                .sum();
    }
}
