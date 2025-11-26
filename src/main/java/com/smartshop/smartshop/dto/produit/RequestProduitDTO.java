package com.smartshop.smartshop.dto.produit;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RequestProduitDTO {
    @NotBlank(message = "Le nom du produit est obligatoire")
    private String nom;

    @NotNull(message = "Le prix unitaire est obligatoire")
    @Positive(message = "Le prix doit être positif")
    private Double prixUnitaire;

    @NotNull(message = "Le stock disponible est obligatoire")
    @PositiveOrZero(message = "Le stock ne peut pas être négatif")
    private Integer stockDisponible;
}
