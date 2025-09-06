package com.project.storage.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "clients")
public class Client extends User{

    @Column(name = "phone", length = 20)
    private String phone;
    
    @Column(name = "address", length = 200)
    private String address;

    public Client(String name, String username, String password, String cpf, 
                  String email, Role role, String phone, String address) {
        super(name, username, password, cpf, email, role);
        this.phone = phone;
        this.address = address;
    }

    public Client(String name, String cpf, String email, Role role, String phone, String address) {
        super(name, null, null, cpf, email, role); // username e password ficam nulos
        this.phone = phone;
        this.address = address;
    }
}
