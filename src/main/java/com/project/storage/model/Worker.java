package com.project.storage.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@ToString(exclude = "sells")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "workers")
public class Worker extends User implements UserDetails {
    
    @Column(name = "department", length = 20, nullable = false)
    private String department;

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sell> sells = new ArrayList<>();

    public Worker(String name, String username, String password, String cpf,
                    String email, Role role, String department){
        super(name, username, password, cpf, email, role);
        this.department = department;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    @Override
    public String getPassword() { return this.password; }

    @Override
    public String getUsername() { return this.username; }
}
