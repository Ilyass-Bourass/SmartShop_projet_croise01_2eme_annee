package com.smartshop.smartshop.dto.paiement;


import com.smartshop.smartshop.enums.TypePaiement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RequestPaiementDTO {
    @NotNull(message = "le montant est obligatoire")
    @PositiveOrZero(message = "le montant doit être positif")
    private Double montant;
    @NotBlank(message = "Le type de paiement est obligatoire")
    private TypePaiement typePaiement;

    private String numeroCheque;
    private String banque;
    private String numeroVirement;
    @NotNull(message = "L'ID de la commande est obligatoire")
    private Long idCommande;
}
