package com.project.storage.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "location")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false) private String name;

    // Pode ser "ALMOXARIFADO" ou "PONTO_VENDA"
    @Column(nullable = false) private String type;
}

