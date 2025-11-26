package com.smartshop.smartshop.service;

import com.smartshop.smartshop.dto.produit.RequestProduitDTO;
import com.smartshop.smartshop.dto.produit.ResponseProduitDTO;

import java.util.List;

public interface ProduitService {
    ResponseProduitDTO createProduit(RequestProduitDTO requestProduitDTO);
    List<ResponseProduitDTO> findAll(Long id);
    String deleteProduit(Long id);
    ResponseProduitDTO updateProduit(RequestProduitDTO requestProduitDTO);
}
