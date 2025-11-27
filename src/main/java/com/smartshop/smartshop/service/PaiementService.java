package com.smartshop.smartshop.service;


import com.smartshop.smartshop.dto.commande.RequestCommandeDTO;
import com.smartshop.smartshop.dto.commande.ResponseCommandeDTO;
import com.smartshop.smartshop.dto.paiement.RequestPaiementDTO;
import com.smartshop.smartshop.dto.paiement.ResponsePaiementDTO;
import com.smartshop.smartshop.entity.Paiement;

import java.util.List;

public interface PaiementService {
    ResponsePaiementDTO createPaiment(RequestPaiementDTO paiementDTO);
    List<ResponsePaiementDTO> findAll();
    String deleteCommande(Long id);
    ResponseCommandeDTO updateCommande(Long id, RequestCommandeDTO requestCommandeDTO);
    ResponseCommandeDTO findCommandeById(Long id);
}
