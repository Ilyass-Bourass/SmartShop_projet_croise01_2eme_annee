package com.smartshop.smartshop.service.implementation;

import com.smartshop.smartshop.dto.commande.RequestCommandeDTO;
import com.smartshop.smartshop.dto.commande.ResponseCommandeDTO;
import com.smartshop.smartshop.dto.paiement.RequestPaiementDTO;
import com.smartshop.smartshop.dto.paiement.ResponsePaiementDTO;
import com.smartshop.smartshop.entity.Commande;
import com.smartshop.smartshop.entity.Paiement;
import com.smartshop.smartshop.enums.StatutCommande;
import com.smartshop.smartshop.enums.StatutPaiement;
import com.smartshop.smartshop.enums.TypePaiement;
import com.smartshop.smartshop.exception.ExceptionConflit;
import com.smartshop.smartshop.exception.ResourceNotFoundException;
import com.smartshop.smartshop.mapper.paiement.PaiementMapper;
import com.smartshop.smartshop.repository.CommandeRepository;
import com.smartshop.smartshop.repository.PaiementRepository;
import com.smartshop.smartshop.service.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;
    private final CommandeRepository commandeRepository;
    private final PaiementMapper paiementMapper;

    @Override
    @Transactional
    public ResponsePaiementDTO createPaiment(RequestPaiementDTO paiementDTO) {
        Commande commande = commandeRepository.findById(paiementDTO.getIdCommande()).orElseThrow(()-> new ResourceNotFoundException("Commande n'existe pas de le id :"+paiementDTO.getIdCommande()));

        if(commande.getStatutCommande()!= StatutCommande.PENDING){
            throw new ExceptionConflit("la commande doit etre en etat PENDING pour effectuer un paiement");
        }

        if(commande.getMontantRestantPayer() == 0){
            throw  new ResourceNotFoundException("la commande est déja payée contacter l'admin pour valider la commande");
        }

        if(paiementDTO.getTypePaiement()==TypePaiement.ESPECES && paiementDTO.getMontant() >=20000.0){
            throw new ExceptionConflit("On peut pas accépter un paiement en espéces superieur ou égal à 20000");
        }

        Double montantRestantAayer;

        if(paiementDTO.getTypePaiement()== TypePaiement.CHEQUE){
            montantRestantAayer= commande.getMontantRestantPayer();
        }

        else if(commande.getMontantRestantPayer()-paiementDTO.getMontant()<=0){
            montantRestantAayer=0.0;
        }else {
         montantRestantAayer=commande.getMontantRestantPayer()-paiementDTO.getMontant();
        }
        commande.setMontantRestantPayer(montantRestantAayer);
        commandeRepository.save(commande);

        Paiement paiement = paiementMapper.toPaiement(paiementDTO);
        paiement.setCommande(commande);
        Paiement saved = paiementRepository.save(paiement);

        return paiementMapper.toResponsePaiementDTO(saved);
    }

    @Override
    public List<ResponsePaiementDTO> findAll() {
        List<Paiement> paiements = paiementRepository.findAll();
        return paiements.stream().map(paiementMapper::toResponsePaiementDTO).toList();
    }

    @Override
    public String deletePaiement(Long id) {
        Paiement paiement = paiementRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Paiement n'existe pas de le id :"+id));
        paiementRepository.delete(paiement);
        return "Paiement supprimé avec succès de l'id :"+id;
    }

    @Override
    public ResponsePaiementDTO updatePaiement(Long id, RequestPaiementDTO requestPaiementDTO) {
        return null;
    }

    @Override
    public ResponsePaiementDTO findCommandeById(Long id) {
        Paiement paiement = paiementRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Paiement n'existe pas de le id :"+id));
        return paiementMapper.toResponsePaiementDTO(paiement);
    }

    @Override
    public String validerPaiementParCheque(Long id) {
        Paiement paiement = paiementRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Paiement n'existe pas de le id :"+id));

        if(paiement.getTypePaiement()!=TypePaiement.CHEQUE){
            throw new ExceptionConflit("le paiement doit etre de type CHEQUE pour etre validé");
        }

            Commande commande = paiement.getCommande();
            if(commande.getStatutCommande()!= StatutCommande.PENDING){
                throw new ExceptionConflit("la commande doit etre en etat PENDING pour valider le paiement");
            }

            if(commande.getMontantRestantPayer() == 0){
                throw  new ResourceNotFoundException("la commande est déja payée contacter l'admin pour valider la commande");
            }

            Double montantRestantAayer;

            if(commande.getMontantRestantPayer()-paiement.getMontant()<=0){
                montantRestantAayer=0.0;
            }else {
                montantRestantAayer=commande.getMontantRestantPayer()-paiement.getMontant();
            }
            commande.setMontantRestantPayer(montantRestantAayer);
            commandeRepository.save(commande);
            paiement.setDateEncaisement(LocalDateTime.now());
            paiement.setStatutPaiement(StatutPaiement.ENCAISSE);
            paiementRepository.save(paiement);

        return "Paiement par cheque validé avec succès pour la commande id :"+commande.getId();
    }

    @Override
    public String refuserPaiementParCheque(Long id) {
        Paiement paiement = paiementRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Paiement n'existe pas de le id :"+id));

        if(paiement.getTypePaiement()!=TypePaiement.CHEQUE || paiement.getStatutPaiement()!= StatutPaiement.ENATTENTE){
            throw new ExceptionConflit("le paiement doit etre de type CHEQUE et en etat ENATTENTE pour etre refusé");
        }
        paiement.setStatutPaiement(StatutPaiement.REJETE);
        paiementRepository.save(paiement);
        return "Paiement par cheque refusé avec succès pour la commande id :"+id;

    }
}
