package com.project.storage.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data @NoArgsConstructor
@ToString(exclude = {"apartment","sells", "client", "group", "guests"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    private LocalDate plannedCheckin;
    private LocalDateTime checkin;
    private LocalDate plannedCheckout;
    private LocalDateTime checkout;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Column(precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(name = "daily_rate", precision = 10, scale = 2, nullable = false)
    private Boolean dailyRate;
   
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

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Guest> guests = new ArrayList<>();

    public Reservation(LocalDateTime checkin, LocalDateTime checkout, Apartment apartment, Client client, ReservationGroup group) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.apartment = apartment;
        this.client = client;
        this.group = group;
        this.status = ReservationStatus.CONFIRMED;
    }
}
