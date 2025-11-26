package com.smartshop.smartshop.mapper.produit;


import com.smartshop.smartshop.dto.produit.RequestProduitDTO;
import com.smartshop.smartshop.dto.produit.ResponseProduitDTO;
import com.smartshop.smartshop.entity.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProduitMapper {

    @Mapping(target = "id", ignore = true)
    Produit toProduit(RequestProduitDTO requestProduitDTO);
    ResponseProduitDTO toResponseProduitDTO(Produit produit);
}
