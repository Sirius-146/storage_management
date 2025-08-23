package com.project.storage.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data @NoArgsConstructor
@ToString(exclude = {"apartment","sells", "client", "group"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private Date checkin;

    @Column(nullable = false)
    private Date checkout;

    @ManyToOne
    @JoinColumn(name = "id_apartment", nullable = false)
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_group")
    private ReservationGroup group;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sell> sells = new ArrayList<>();

    public Reservation(Date checkin, Date checkout, Apartment apartment, Client client, ReservationGroup group) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.apartment = apartment;
        this.client = client;
        this.group = group;
    }
}
