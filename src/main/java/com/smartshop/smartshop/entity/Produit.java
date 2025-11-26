package com.smartshop.smartshop.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "produits")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nom;

    @Column(nullable = false)
    private Double prixUnitaire;

    @Column(nullable = false)
    private Integer stockDisponible;

    private Boolean isExiste;

    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "produit")
    private List<LigneCommande> ligneCommandes;

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
