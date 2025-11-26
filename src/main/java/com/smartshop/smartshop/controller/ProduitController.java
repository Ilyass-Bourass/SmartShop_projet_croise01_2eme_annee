package com.smartshop.smartshop.controller;


import com.smartshop.smartshop.dto.produit.RequestProduitDTO;
import com.smartshop.smartshop.dto.produit.ResponseProduitDTO;
import com.smartshop.smartshop.service.ProduitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<ResponseProduitDTO>> findAllProduits(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam (required = false) Boolean isExiste
    ) {
        List<ResponseProduitDTO> produits = produitService.findAll(page, size, isExiste);
        return new ResponseEntity<>(produits, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduit(@PathVariable Long id) {
        String message = produitService.deleteProduit(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
