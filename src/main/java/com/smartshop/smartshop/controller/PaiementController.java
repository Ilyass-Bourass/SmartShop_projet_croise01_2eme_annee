package com.smartshop.smartshop.controller;


import com.smartshop.smartshop.dto.paiement.RequestPaiementDTO;
import com.smartshop.smartshop.dto.paiement.ResponsePaiementDTO;
import com.smartshop.smartshop.service.PaiementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
