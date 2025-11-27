package com.smartshop.smartshop.controller;


import com.smartshop.smartshop.dto.paiement.RequestPaiementDTO;
import com.smartshop.smartshop.dto.paiement.ResponsePaiementDTO;
import com.smartshop.smartshop.service.PaiementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/validerCheque/{id}")
    public ResponseEntity<String> validerPaiementParCheque(@PathVariable Long id) {
        String response = paiementService.validerPaiementParCheque(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
