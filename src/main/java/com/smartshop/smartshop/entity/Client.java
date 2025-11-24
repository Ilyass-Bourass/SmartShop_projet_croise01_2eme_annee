package com.smartshop.smartshop.entity;

import com.smartshop.smartshop.enums.NiveauFidelite;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "clients")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Utilisateur {

    private String nom;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String adresse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NiveauFidelite niveauFidelite;

    @Column(nullable = false)
    private Integer nombreTotalCommandes;

    @Column(nullable = false)
    private Double montantTotalCumule;

    private LocalDateTime datePremiereCommande;

    private LocalDateTime dateDerniereCommande;

    private LocalDateTime dateModification;

    @Column(nullable = false)
    private Boolean estActif;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Commande> commandes;

    public void prePersist() {
        super.prePersist();
        nombreTotalCommandes = 0;
        montantTotalCumule = 0.0;
        niveauFidelite = NiveauFidelite.BASIC;
        estActif = true;
    }

    @PreUpdate
    public void preUpdate() {
        dateModification = LocalDateTime.now();
    }
}
