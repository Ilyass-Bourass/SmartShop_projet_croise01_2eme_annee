package com.smartshop.smartshop.service;

import com.smartshop.smartshop.dto.commande.RequestCommandeDTO;
import com.smartshop.smartshop.dto.commande.ResponseCommandeDTO;
import com.smartshop.smartshop.dto.ligneCommande.ResponseLigneCommandeDTO;

import java.util.List;

public interface CommandeService {
    ResponseCommandeDTO createCommande(RequestCommandeDTO requestCommandeDTO);
    List<ResponseCommandeDTO> findAllCommandes();
    List<ResponseCommandeDTO> findAllCommandesClient(long idClient);
    String deleteCommande(Long id);
    ResponseCommandeDTO updateCommande(Long id, RequestCommandeDTO requestCommandeDTO);
    ResponseCommandeDTO findCommandeById(Long id);

}
