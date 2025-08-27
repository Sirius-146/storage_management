package com.project.storage.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;
    private String cpf;
    private String phone;
    private String email;
    private String address;

    public Client(String name ,String cpf,  String phone, String email, String address){
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.email = email;
        this.address =address;
    }
}
