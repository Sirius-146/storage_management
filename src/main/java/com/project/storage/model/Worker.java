package com.project.storage.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "workers")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "sells")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "sector")
    private String sector;

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sell> sells = new ArrayList<>();


    public Worker(String name, String sector){
        this.name = name;
        this.sector = sector;
    }
}
