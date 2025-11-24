package com.smartshop.smartshop.entity;

import com.smartshop.smartshop.enums.StatutPaiement;
import com.smartshop.smartshop.enums.TypePaiement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "paiements")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numeroPaiment;
    @Column(nullable = false)
    private Double montant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypePaiement  typePaiement;

    @Enumerated(EnumType.STRING)
    private StatutPaiement statutPaiement;

    private LocalDateTime dateEncaisement;
    private LocalDateTime datePaiement;

    private String numeroCheque;
    private String banque;

    private String numeroVirement;


    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    @PrePersist
    public void prePersist() {
        datePaiement = LocalDateTime.now();
    }
}
