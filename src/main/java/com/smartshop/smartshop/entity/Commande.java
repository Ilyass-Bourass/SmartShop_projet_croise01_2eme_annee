package com.smartshop.smartshop.entity;


import com.smartshop.smartshop.enums.StatutCommande;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "commandes")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private Double montantHTApresRemise;
    private Double tauxTVA;
    private Double montantTVA;
    private Double montantTTC;
    private LocalDateTime dateValidation;

    private Double montantRestantAPayer;
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
        montantRestantAPayer = montantTTC;
    }

    @PreUpdate
    public void preUpdate() {
        dateModification = LocalDateTime.now();
    }


}
