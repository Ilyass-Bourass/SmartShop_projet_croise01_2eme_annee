package com.smartshop.smartshop.controller;


import com.smartshop.smartshop.dto.produit.RequestProduitDTO;
import com.smartshop.smartshop.dto.produit.ResponseProduitDTO;
import com.smartshop.smartshop.service.ProduitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor

public class ProduitController {
    private final ProduitService produitService;

    @PostMapping
    public ResponseEntity<ResponseProduitDTO> saveProduit(@Valid @RequestBody RequestProduitDTO requestProduitDTO) {
        ResponseProduitDTO responseProduitDTO = produitService.createProduit(requestProduitDTO);
        return new ResponseEntity<>(responseProduitDTO, HttpStatus.CREATED);
    }
}
