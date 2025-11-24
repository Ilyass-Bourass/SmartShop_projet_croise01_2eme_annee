package com.smartshop.smartshop.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ligne_commandes")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class LigneCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantite;
    private Double prixUnitaire;
}
