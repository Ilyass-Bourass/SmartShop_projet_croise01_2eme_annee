package com.smartshop.smartshop.service.implementation;

import com.smartshop.smartshop.dto.commande.RequestCommandeDTO;
import com.smartshop.smartshop.dto.commande.ResponseCommandeDTO;
import com.smartshop.smartshop.dto.ligneCommande.ResponseLigneCommandeDTO;
import com.smartshop.smartshop.entity.Client;
import com.smartshop.smartshop.entity.Commande;
import com.smartshop.smartshop.entity.LigneCommande;
import com.smartshop.smartshop.entity.Produit;
import com.smartshop.smartshop.enums.NiveauFidelite;
import com.smartshop.smartshop.enums.StatutCommande;
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

            if(produit.getStockDisponible() < ligneCommandeRequest.getQuantite()){
                throw new ResourceNotFoundException("Le stock actuel du produit de id : " + ligneCommandeRequest.getProduitId()+" est :"+produit.getStockDisponible()+ " et votre commande est :"+ligneCommandeRequest.getQuantite());
            }

            LigneCommande ligneCommande = new LigneCommande();
            ligneCommande.setProduit(produit);
            ligneCommande.setQuantite(ligneCommandeRequest.getQuantite());
            ligneCommande.setPrixUnitaire(ligneCommandeRequest.getPrixUnitaire());
            ligneCommande.setCommande(commande);
            ligneCommandes.add(ligneCommande);
            sousTotal += ligneCommande.getPrixUnitaire() * ligneCommande.getQuantite();
        }

        Double montantRemise = 0.0;

        commande.setLigneCommandes(ligneCommandes);
        montantRemise=applierRemiseFidelite(client,sousTotal);
        Double montantHtApresRemise = sousTotal -montantRemise;

        Double montantTva = montantHtApresRemise * (requestCommandeDTO.getTauxTva() / 100);
        Double montantTtc = montantHtApresRemise + montantTva;
        commande.setSousTotal(sousTotal);
        commande.setMontantRemise(montantRemise);
        commande.setMontantHtApresRemise(montantHtApresRemise);
        commande.setMontantTva(montantTva);
        commande.setMontantTtc(montantTtc);
        commande.setMontantRestantPayer(montantTtc);

        Commande savedCommande = commandeRepository.save(commande);
        return commandeMapper.toResponseCommandeDTO(savedCommande);
    }

    @Override
    public List<ResponseCommandeDTO> findAllCommandes() {
        List<Commande> commandes = commandeRepository.findAll();
        return commandes.stream()
                .map(commandeMapper::toResponseCommandeDTO)
                .toList();
    }

    @Override
    public List<ResponseCommandeDTO> findAllCommandesClient(long idClient) {
        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new ResourceNotFoundException("Client n'existe pas avec l'id : " + idClient));

        List<ResponseCommandeDTO> commandes = findAllCommandes();
        List<ResponseCommandeDTO> commandesClient = commandes.stream()
                .filter(commande -> commande.getIdClient() == idClient)
                .toList();
        return commandesClient;
    }

    @Override
    public String deleteCommande(Long id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande n'existe pas avec l'id : " + id));
        commandeRepository.delete(commande);
        return "la commande a été supprimée avec succès! de l'id : " + id;
    }

    @Override
    public ResponseCommandeDTO updateCommande(Long id, RequestCommandeDTO requestCommandeDTO) {
        return null;
    }

    @Override
    public ResponseCommandeDTO findCommandeById(Long id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande n'existe pas avec l'id : " + id));

        return commandeMapper.toResponseCommandeDTO(commande);
    }

    private Double applierRemiseFidelite(Client client,Double sousTotal) {
        Double montantRemise = 0.0;
        if(client.getNiveauFidelite().equals(NiveauFidelite.SILVER) && sousTotal >=500){
            montantRemise=sousTotal*0.05;
        } else if (client.getNiveauFidelite().equals(NiveauFidelite.GOLD) && sousTotal >=800) {
            montantRemise=sousTotal*0.10;
        } else if (client.getNiveauFidelite().equals(NiveauFidelite.PLATINUM) && sousTotal >= 1200) {
            montantRemise=sousTotal*0.15;
        }
        return montantRemise;
    }
}
