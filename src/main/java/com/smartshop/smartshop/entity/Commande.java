package com.smartshop.smartshop.entity;


import com.smartshop.smartshop.enums.StatutCommande;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "commandes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroCommande;

    @Enumerated(EnumType.STRING)
    private StatutCommande statutCommande;

    private Double sousTotal;
    private Double montantRemise;
    private Double montantHtApresRemise;
    private Double tauxTva;
    private Double montantTva;
    private Double montantTtc;
    private LocalDateTime dateValidation;

    private Double montantRestantPayer;
    private LocalDateTime dateCreation ;
    private LocalDateTime dateModification;

    private String codePromo;


    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Paiement> paiements;


    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LigneCommande> ligneCommandes;

    @PrePersist
    public void prePersist() {
        dateCreation = LocalDateTime.now();
        statutCommande = StatutCommande.PENDING;
        montantRestantPayer = montantTtc;
    }

    @PreUpdate
    public void preUpdate() {
        dateModification = LocalDateTime.now();
    }


}
