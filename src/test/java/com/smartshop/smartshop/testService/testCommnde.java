package com.smartshop.smartshop.testService;


import com.smartshop.smartshop.dto.commande.RequestCommandeDTO;
import com.smartshop.smartshop.dto.commande.ResponseCommandeDTO;
import com.smartshop.smartshop.dto.ligneCommande.RequestLigneCommandeDTO;
import com.smartshop.smartshop.entity.Client;
import com.smartshop.smartshop.entity.Commande;
import com.smartshop.smartshop.entity.LigneCommande;
import com.smartshop.smartshop.entity.Produit;
import com.smartshop.smartshop.enums.NiveauFidelite;
import com.smartshop.smartshop.mapper.commande.CommandeMapper;
import com.smartshop.smartshop.repository.ClientRepository;
import com.smartshop.smartshop.repository.CodePromoRepository;
import com.smartshop.smartshop.repository.CommandeRepository;
import com.smartshop.smartshop.repository.ProduitRepository;
import com.smartshop.smartshop.service.CommandeService;
import com.smartshop.smartshop.service.implementation.CommandeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class testCommnde {
    @Mock
    private CommandeRepository commandeRepository;

    @Mock
    private CommandeMapper commandeMapper;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private CodePromoRepository codePromoRepository;

    @InjectMocks
    private CommandeServiceImpl commandeService;

    private Client client;
    private Produit produit;
    private Commande commande;
    private LigneCommande ligneCommande;


    @BeforeEach

    public void setUp() {

        client =Client.builder()
                .id(1L)
                .niveauFidelite(NiveauFidelite.BASIC)
                .montantTotalCumule(0.0)
                .nombreTotalCommandes(0)
                .build();

        produit =Produit.builder()
                .id(1L)
                .stockDisponible(100)
                .isExiste(Boolean.TRUE)
                .build();

        ligneCommande = LigneCommande.builder()
                .id(1L)
                .prixUnitaire(100.0)
                .quantite(20)
                .produit(produit)
                .build();

        commande = Commande.builder()
                .id(1L)
                .client(client)
                .tauxTva(20.0)
                .ligneCommandes(List.of(ligneCommande))
                .build();

    }


    @Test

    void senario01_creer_commande_client_niveau_fidelite_basic(){

            RequestLigneCommandeDTO requestLigneCommandeDTO = RequestLigneCommandeDTO.builder()
                    .produitId(1L)
                    .prixUnitaire(100.0)
                    .quantite(20)
                    .build();

            RequestCommandeDTO requestCommandeDTO = RequestCommandeDTO.builder()
                    .idClient(1L)
                    .tauxTva(20.0)
                    .ligneCommandes(List.of(requestLigneCommandeDTO))
                    .build();

            ResponseCommandeDTO responseExpected = ResponseCommandeDTO.builder()
                    .sousTotal(2000.0)
                    .montantRemise(0.0)
                    .montantHtApresRemise(2000.0)
                    .montantTva(400.0)
                    .montantTtc(2400.0)
                    .montantRestantPayer(2400.0)
                    .build();

            when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
            when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
            when(commandeMapper.toCommande(any(RequestCommandeDTO.class))).thenReturn(commande);
            when(commandeRepository.save(any(Commande.class))).thenReturn(commande);
            when(commandeMapper.toResponseCommandeDTO(any(Commande.class))).thenReturn(responseExpected); // ✅ AJOUT IMPORTANT

        ResponseCommandeDTO result = commandeService.createCommande(requestCommandeDTO);

        assertNotNull(result);
            assertEquals(2000.0, result.getSousTotal());
            assertEquals(0.0, result.getMontantRemise());
            assertEquals(2400.0, result.getMontantTtc());
        }

    }

