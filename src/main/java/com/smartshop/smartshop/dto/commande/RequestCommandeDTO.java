package com.smartshop.smartshop.dto.commande;


import com.smartshop.smartshop.dto.ligneCommande.RequestLigneCommandeDTO;
import com.smartshop.smartshop.dto.ligneCommande.ResponseLigneCommandeDTO;
import com.smartshop.smartshop.entity.LigneCommande;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RequestCommandeDTO {

    private String codePromo;

    @NotNull(message = "L'identifiant du client est obligatoire")
    private Long idClient;
    @NotNull(message = "Le taux de TVA est obligatoire")
    private Double tauxTva;

    private List<RequestLigneCommandeDTO> ligneCommandes;
}
