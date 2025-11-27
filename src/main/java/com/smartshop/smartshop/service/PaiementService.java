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
    ResponsePaiementDTO updatePaiement(Long id, RequestPaiementDTO requestPaiementDTO);
    ResponsePaiementDTO findCommandeById(Long id);
    String validerPaiementParCheque(Long id);
    String refuserPaiementParCheque(Long id);
}
