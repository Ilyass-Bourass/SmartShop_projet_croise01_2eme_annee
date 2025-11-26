package com.smartshop.smartshop.mapper.ligneCommande;

import com.smartshop.smartshop.dto.ligneCommande.RequestLigneCommandeDTO;
import com.smartshop.smartshop.dto.ligneCommande.ResponseLigneCommandeDTO;
import com.smartshop.smartshop.entity.LigneCommande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LigneCommandeMapper {

    @Mapping(target = "produitId", source = "produit.id")
    @Mapping(target = "nomProduit", source = "produit.nom")
    ResponseLigneCommandeDTO toResponseLigneCommandeDTO(LigneCommande ligneCommande);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commande", ignore = true)
    @Mapping(target = "produit", ignore = true)
    LigneCommande toLigneCommande(RequestLigneCommandeDTO requestLigneCommandeDTO);
}
