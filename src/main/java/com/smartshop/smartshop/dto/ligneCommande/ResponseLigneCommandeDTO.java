package com.smartshop.smartshop.dto.ligneCommande;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResponseLigneCommandeDTO {
    private Long id;
    private Integer quantite;
    private Double prixUnitaire;
    private Long produitId;
    private String nomProduit;
}
