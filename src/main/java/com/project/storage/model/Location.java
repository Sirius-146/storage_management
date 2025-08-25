package com.project.storage.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 15, nullable = false)
    private String name;

    // Pode ser "ALMOXARIFADO" ou "PONTO_VENDA"
    @Column(length = 15, nullable = false)
    private String type;
}

