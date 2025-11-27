package com.smartshop.smartshop.mapper.paiement;

import com.smartshop.smartshop.dto.paiement.RequestPaiementDTO;
import com.smartshop.smartshop.dto.paiement.ResponsePaiementDTO;
import com.smartshop.smartshop.entity.Paiement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PaiementMapper {

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "commande", ignore = true)
    Paiement toPaiement(RequestPaiementDTO requestPaiementDTO);


    @Mapping(target = "idCommande",source = "commande.id")
    @Mapping(target ="numeroCommande" ,source = "commande.numeroCommande")
    ResponsePaiementDTO toResponsePaiementDTO(Paiement paiement);
}
