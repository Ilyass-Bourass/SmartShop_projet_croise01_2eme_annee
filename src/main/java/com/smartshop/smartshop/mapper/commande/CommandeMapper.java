package com.smartshop.smartshop.mapper.commande;


import com.smartshop.smartshop.dto.commande.RequestCommandeDTO;
import com.smartshop.smartshop.dto.commande.ResponseCommandeDTO;
import com.smartshop.smartshop.entity.Commande;
import com.smartshop.smartshop.mapper.ligneCommande.LigneCommandeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LigneCommandeMapper.class})
public interface CommandeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target ="numeroCommande" , expression = "java(creerNumeroCommande())")
    @Mapping(target = "ligneCommandes", ignore = true)
    Commande toCommande(RequestCommandeDTO requestCommandeDTO);

    @Mapping(target = "idClient", source = "client.id")
    @Mapping(target = "nomClient", source = "client.nom")
    @Mapping(target = "niveauFidelite",source = "client.niveauFidelite")
    ResponseCommandeDTO toResponseCommandeDTO(Commande commande);


    default String creerNumeroCommande(){
        String numeroCommande = "CMD-"+ java.util.UUID.randomUUID().toString().substring(0,7);
        return numeroCommande;
    }
}
