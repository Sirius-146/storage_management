package com.project.storage.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@ToString(exclude = {"sells", "client"})
@Entity
@Table(name = "reservations")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private Date checkin;

    @Column(nullable = false)
    private Date checkout;

    @Column(nullable = false)
    private Integer apartment;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sell> sells = new ArrayList<>();

    public Reservation(Date checkin, Date checkout, Integer apartment, Client client) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.apartment = apartment;
        this.client = client;
    }
}
