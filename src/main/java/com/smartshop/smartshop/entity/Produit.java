package com.smartshop.smartshop.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "produits")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @Column(nullable = false)
    private Double prixUnitaire;

    @Column(nullable = false)
    private Integer stockDisponible;

    private Boolean isExiste;

    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    @PrePersist
    public void prePersist() {
        dateCreation = LocalDateTime.now();
        isExiste = true;
    }

    @PreUpdate
    public void preUpdate() {
        dateModification = LocalDateTime.now();
    }
}
