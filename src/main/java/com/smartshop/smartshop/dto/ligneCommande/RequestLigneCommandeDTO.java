package com.smartshop.smartshop.dto.ligneCommande;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RequestLigneCommandeDTO {
    @NotNull(message = "La quantité ne peut pas être nulle")
    @PositiveOrZero(message = "La quantité doit être positive ou nulle")
    private Integer quantite;
    @NotNull(message = "Le prix unitaire ne peut pas être nul")
    @PositiveOrZero(message = "Le prix unitaire doit être positif ou nul")
    private Double prixUnitaire;
    @NotNull(message = "L'ID du produit ne peut pas être nul")
    private Long produitId;
}
