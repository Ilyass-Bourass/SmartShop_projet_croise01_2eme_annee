package com.smartshop.smartshop.controller;


import com.smartshop.smartshop.dto.paiement.RequestPaiementDTO;
import com.smartshop.smartshop.dto.paiement.ResponsePaiementDTO;
import com.smartshop.smartshop.service.PaiementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/paiements")

public class PaiementController {
    private final PaiementService paiementService;

    @PostMapping
    public ResponseEntity<ResponsePaiementDTO> savePaiement(@Valid @RequestBody RequestPaiementDTO requestPaiementDTO) {
        ResponsePaiementDTO responsePaiementDTO = paiementService.createPaiment(requestPaiementDTO);
        return new ResponseEntity<>(responsePaiementDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponsePaiementDTO>> getAllPaiements() {
        java.util.List<ResponsePaiementDTO> paiements = paiementService.findAll();
        return new ResponseEntity<>(paiements, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePaiementDTO> getPaiementById(@PathVariable Long id) {
        ResponsePaiementDTO paiement = paiementService.findCommandeById(id);
        return new ResponseEntity<>(paiement, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaiement(@PathVariable Long id) {
        String response = paiementService.deletePaiement(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/validerCheque/{id}")
    public ResponseEntity<String> validerPaiementParCheque(@PathVariable Long id) {
        String response = paiementService.validerPaiementParCheque(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/refuserCheque/{id}")
    public ResponseEntity<String> refuserPaiementParCheque(@PathVariable Long id) {
        String response = paiementService.refuserPaiementParCheque(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
