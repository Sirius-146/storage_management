package com.project.storage.model;

import jakarta.persistence.*;
import lombok.*;;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    protected String name;
    @Column(name = "username", unique = true, nullable = false, length = 50)
    protected String username;
    @Column(name = "password", nullable = false)
    protected String password;
    @Column(name = "cpf", unique = true, nullable = false, length = 11)
    protected String cpf;
    @Column(name = "email", unique = true, nullable = false, length = 100)
    protected String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 15, nullable = false)
    protected Role role;
    
    public User(String name, String username, String password, String cpf, String email, Role role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.cpf = cpf;
        this.email = email;
        this.role = role;
    }
}