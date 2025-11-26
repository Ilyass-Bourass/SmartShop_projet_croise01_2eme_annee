package com.smartshop.smartshop.dto.commande;


import com.smartshop.smartshop.dto.ligneCommande.ResponseLigneCommandeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResponseCommandeDTO {
    private Long id;
    private String numeroCommande;
    private Long idClient;
    private String nomClient;
    private String codePromo;
    private List<ResponseLigneCommandeDTO> ligneCommandes;
}
