package com.smartshop.smartshop.controller;


import com.smartshop.smartshop.dto.commande.RequestCommandeDTO;
import com.smartshop.smartshop.dto.commande.ResponseCommandeDTO;
import com.smartshop.smartshop.entity.Commande;
import com.smartshop.smartshop.service.CommandeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/commandes")

public class CommandeController {
    private final CommandeService commandeService;

    @PostMapping
    public ResponseEntity<ResponseCommandeDTO> saveCommande(@Valid @RequestBody RequestCommandeDTO requestCommandeDTO) {
        ResponseCommandeDTO savedCommande = commandeService.createCommande(requestCommandeDTO);
        return new ResponseEntity<>(savedCommande, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseCommandeDTO>> getAllCommandes() {
        java.util.List<ResponseCommandeDTO> commandes = commandeService.findAllCommandes();
        return new ResponseEntity<>(commandes, HttpStatus.OK);
    }
}
