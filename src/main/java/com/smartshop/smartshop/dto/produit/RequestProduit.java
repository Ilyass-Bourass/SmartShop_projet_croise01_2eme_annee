package com.smartshop.smartshop.dto.produit;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RequestProduit {
    @NotBlank(message = "Le nom du produit est obligatoire")
    private String nom;
    @NotBlank(message = "Le prix unitaire est obligatoire")
    private Double prixUnitaire;
    @NotBlank(message = "Le stock disponible est obligatoire")
    private Integer stockDisponible;
}
