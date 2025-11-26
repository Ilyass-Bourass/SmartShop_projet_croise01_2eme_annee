package com.smartshop.smartshop.entity;

import com.smartshop.smartshop.enums.RoleUtilisateur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "utilisateurs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(unique = true, nullable = false)
    protected String email;

    @Column(nullable = false)
    protected String motDePasse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected RoleUtilisateur role;

    @Column(nullable = false)
    protected LocalDateTime dateCreation;

    @PrePersist
    public void prePersist() {
        dateCreation = LocalDateTime.now();
    }
}
