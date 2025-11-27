package com.smartshop.smartshop.dto.paiement;

import com.smartshop.smartshop.entity.Commande;
import com.smartshop.smartshop.enums.StatutPaiement;
import com.smartshop.smartshop.enums.TypePaiement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResponsePaiementDTO {
    private Long id;
    private String numeroPaiment;
    private Double montant;
    private TypePaiement typePaiement;
    private StatutPaiement statutPaiement;
    private LocalDateTime dateEncaisement;
    private LocalDateTime datePaiement;
    private String numeroCheque;
    private String banque;
    private String numeroVirement;
    private Long idCommande;
    private String numeroCommande;
}
