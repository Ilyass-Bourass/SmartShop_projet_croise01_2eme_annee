package com.smartshop.smartshop.dto.commande;


import com.smartshop.smartshop.dto.ligneCommande.ResponseLigneCommandeDTO;
import com.smartshop.smartshop.enums.StatutCommande;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ResponseCommandeDTO {
    private Long id;
    private String numeroCommande;
    private StatutCommande statutCommande;
    private Long idClient;
    private String nomClient;
    private String niveauFidelite;
    private String codePromo;
    private Double sousTotal;
    private Double montantRemise;
    private Double montantHtApresRemise;
    private Double tauxTva;
    private Double montantTva;
    private Double montantTtc;
    private LocalDateTime dateValidation;

    private Double montantRestantPayer;
    private LocalDateTime dateCreation ;
    private LocalDateTime dateModification;
    private List<ResponseLigneCommandeDTO> ligneCommandes;
}
