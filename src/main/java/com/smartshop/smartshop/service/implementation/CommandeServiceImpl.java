package com.smartshop.smartshop.service.implementation;

import com.smartshop.smartshop.dto.commande.RequestCommandeDTO;
import com.smartshop.smartshop.dto.commande.ResponseCommandeDTO;
import com.smartshop.smartshop.dto.ligneCommande.ResponseLigneCommandeDTO;
import com.smartshop.smartshop.entity.*;
import com.smartshop.smartshop.enums.NiveauFidelite;
import com.smartshop.smartshop.enums.StatutCommande;
import com.smartshop.smartshop.exception.ExceptionConflit;
import com.smartshop.smartshop.exception.ResourceNotFoundException;
import com.smartshop.smartshop.mapper.commande.CommandeMapper;
import com.smartshop.smartshop.repository.ClientRepository;
import com.smartshop.smartshop.repository.CodePromoRepository;
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
    private final CodePromoRepository codePromoRepository;

    @Override
    @Transactional
    public ResponseCommandeDTO createCommande(RequestCommandeDTO requestCommandeDTO) {

        Client client = clientRepository.findById(requestCommandeDTO.getIdClient())
                .orElseThrow(() -> new ResourceNotFoundException("Client n'existe pas avec l'id : " + requestCommandeDTO.getIdClient()));


        Commande commande = commandeMapper.toCommande(requestCommandeDTO);
        commande.setClient(client);

        double sousTotal = 0.0;

        List<LigneCommande> ligneCommandes = new ArrayList<>();

        for (var ligneCommandeRequest : requestCommandeDTO.getLigneCommandes()) {
            Produit produit = produitRepository.findById(ligneCommandeRequest.getProduitId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produit n'existe pas avec l'id : " + ligneCommandeRequest.getProduitId()));

            if (produit.getStockDisponible() < ligneCommandeRequest.getQuantite()) {
                throw new ResourceNotFoundException("Le stock actuel du produit de id : " + ligneCommandeRequest.getProduitId() + " est :" + produit.getStockDisponible() + " et votre commande est :" + ligneCommandeRequest.getQuantite());
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

        boolean activerCodePromo = false;

        String codePromorequest = requestCommandeDTO.getCodePromo();

        if(codePromorequest != null && !codePromorequest.isEmpty()) {
            CodePromo promo = codePromoRepository.findByCodePromo(codePromorequest);

            if(promo != null && Boolean.TRUE.equals(promo.getIsActif())) {
                activerCodePromo = true;
            }
        }


        montantRemise = applierRemiseFidelite(client, sousTotal, activerCodePromo);

        Double montantHtApresRemise = sousTotal - montantRemise;

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

    @Override
    public String annulerCommande(Long id) {
        Commande commande = commandeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Commande n'existe pas"));
        if (commande.getStatutCommande() != StatutCommande.PENDING) {
            throw new ResourceNotFoundException("la commande n'est pas de statut pendding");
        }
        commande.setStatutCommande(StatutCommande.CANCELLED);
        commandeRepository.save(commande);
        return "la commande est annuler avec succée";
    }

    @Override
    @Transactional
    public ResponseCommandeDTO validerCommande(Long id) {
        Commande commande = commandeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Commande n'existe pas"));

        if (commande.getStatutCommande() != StatutCommande.PENDING) {
            throw new ExceptionConflit("la commande n'est pas de statut pendding");
        }

        if (commande.getMontantRestantPayer() != 0) {
            throw new ExceptionConflit("la commande n'est pas encore payée");
        }

        commande.getLigneCommandes().forEach(ligneCommande -> {
            Produit produit = ligneCommande.getProduit();
            produit.setStockDisponible(produit.getStockDisponible() - ligneCommande.getQuantite());
            produitRepository.save(produit);
        });

        Client client = commande.getClient();
        appliquerNiveauFidelite(client, commande.getMontantTtc());

        commande.setStatutCommande(StatutCommande.CONFIRMED);
        Commande savedCommande = commandeRepository.save(commande);
        return commandeMapper.toResponseCommandeDTO(savedCommande);
    }

    private Double applierRemiseFidelite(Client client, Double sousTotal,Boolean activerCodePromo) {
        Double montantRemise = 0.0;
        Double remiseCodePromo=0.0;
        if(activerCodePromo){
            remiseCodePromo=0.05;
        }
        if (client.getNiveauFidelite().equals(NiveauFidelite.SILVER) && sousTotal >= 500) {
            montantRemise = sousTotal * (0.05+remiseCodePromo);
        } else if (client.getNiveauFidelite().equals(NiveauFidelite.GOLD) && sousTotal >= 800) {
            montantRemise = sousTotal * (0.10+remiseCodePromo);
        } else if (client.getNiveauFidelite().equals(NiveauFidelite.PLATINUM) && sousTotal >= 1200) {
            montantRemise = sousTotal * (0.15+remiseCodePromo);
        }
        return montantRemise;
    }

    private void appliquerNiveauFidelite(Client client, Double montantTtc) {

        String ancienNiveau = client.getNiveauFidelite().name();
        if (client.getMontantTotalCumule() +montantTtc >= 15000 || client.getNombreTotalCommandes()>=19) {
            client.setNiveauFidelite(NiveauFidelite.PLATINUM);

        } else if (client.getMontantTotalCumule() +montantTtc >= 5000  || client.getNombreTotalCommandes()>=9 ) {
            client.setNiveauFidelite(NiveauFidelite.GOLD);
        }

        else if (client.getMontantTotalCumule() + montantTtc >= 1000 || client.getNombreTotalCommandes()>=2) {
            client.setNiveauFidelite(NiveauFidelite.SILVER);
        }

        client.setMontantTotalCumule(client.getMontantTotalCumule()+montantTtc);
        client.setNombreTotalCommandes(client.getNombreTotalCommandes()+1);
        clientRepository.save(client);
    }
}
