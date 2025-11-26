package com.smartshop.smartshop.dto.produit;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResponseProduit {
    private Long id;
    private String nom;
    private Double prixUnitaire;
    private Integer stockDisponible;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
}
