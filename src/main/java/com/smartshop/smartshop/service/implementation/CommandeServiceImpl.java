package com.smartshop.smartshop.service.implementation;

import com.smartshop.smartshop.dto.commande.RequestCommandeDTO;
import com.smartshop.smartshop.dto.commande.ResponseCommandeDTO;
import com.smartshop.smartshop.entity.Client;
import com.smartshop.smartshop.entity.Commande;
import com.smartshop.smartshop.entity.LigneCommande;
import com.smartshop.smartshop.entity.Produit;
import com.smartshop.smartshop.exception.ResourceNotFoundException;
import com.smartshop.smartshop.mapper.commande.CommandeMapper;
import com.smartshop.smartshop.repository.ClientRepository;
import com.smartshop.smartshop.repository.CommandeRepository;
import com.smartshop.smartshop.repository.ProduitRepository;
import com.smartshop.smartshop.service.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandeServiceImpl implements CommandeService {
    private final CommandeRepository commandeRepository;
    private final CommandeMapper commandeMapper;
    private final ClientRepository clientRepository;
    private final ProduitRepository produitRepository;

    @Override
    @Transactional
    public ResponseCommandeDTO createCommande(RequestCommandeDTO requestCommandeDTO) {

        Client client = clientRepository.findById(requestCommandeDTO.getIdClient())
                .orElseThrow(() -> new ResourceNotFoundException("Client n'existe pas avec l'id : " + requestCommandeDTO.getIdClient()));


        Commande commande = commandeMapper.toCommande(requestCommandeDTO);
        commande.setClient(client);

        double sousTotal = 0.0;

        List<LigneCommande> ligneCommandes = new ArrayList<>();

        for(var ligneCommandeRequest : requestCommandeDTO.getLigneCommandes()){
            Produit produit = produitRepository.findById(ligneCommandeRequest.getProduitId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produit n'existe pas avec l'id : " + ligneCommandeRequest.getProduitId()));
            LigneCommande ligneCommande = new LigneCommande();
            ligneCommande.setProduit(produit);
            ligneCommande.setQuantite(ligneCommandeRequest.getQuantite());
            ligneCommande.setPrixUnitaire(ligneCommandeRequest.getPrixUnitaire());
            ligneCommande.setCommande(commande);
            ligneCommandes.add(ligneCommande);
            sousTotal += ligneCommande.getPrixUnitaire() * ligneCommande.getQuantite();
        }

        Double montantTva = sousTotal * (requestCommandeDTO.getTauxTva() / 100);
        Double montantTtc = sousTotal + montantTva;

        commande.setLigneCommandes(ligneCommandes);
        commande.setSousTotal(sousTotal);
        commande.setMontantTva(montantTva);
        commande.setMontantTtc(montantTtc);
        commande.setMontantRestantPayer(montantTtc);

        Commande savedCommande = commandeRepository.save(commande);
        return commandeMapper.toResponseCommandeDTO(savedCommande);
    }

    @Override
    public List<ResponseCommandeDTO> findAllCommandes() {
        return List.of();
    }

    @Override
    public String deleteCommande(Long id) {
        return "";
    }

    @Override
    public ResponseCommandeDTO updateCommande(Long id, RequestCommandeDTO requestCommandeDTO) {
        return null;
    }

    @Override
    public ResponseCommandeDTO findCommandeById(Long id) {
        return null;
    }
}
