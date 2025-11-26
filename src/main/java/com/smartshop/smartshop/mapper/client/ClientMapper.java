package com.smartshop.smartshop.mapper.client;


import com.smartshop.smartshop.dto.client.RequestClient;
import com.smartshop.smartshop.dto.client.ResponseClient;
import com.smartshop.smartshop.entity.Client;
import com.smartshop.smartshop.enums.RoleUtilisateur;
import jakarta.validation.groups.Default;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import org.mindrot.jbcrypt.BCrypt;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    /*
    default Client toClient(RequestClient requestClient) {
        Client client = new Client();
        client.setEmail(requestClient.getEmail());
        client.setMotDePasse(BCrypt.hashpw(requestClient.getMotDePasse(), BCrypt.gensalt()));
        client.setRole(RoleUtilisateur.CLIENT);
        client.setNom(requestClient.getNom());
        client.setTelephone(requestClient.getTelephone());
        client.setAdresse(requestClient.getAdresse());
        return client;
    }
     */
    ResponseClient toResponseClient(Client client);

    @Mapping(target = "motDePasse", qualifiedByName = "hashPassword")
    @Mapping(target = "role", constant = "CLIENT")
    Client toClient(RequestClient requestClient);

    @Named("hashPassword")
    default  String hashPassword(String motDePasse) {
        return BCrypt.hashpw(motDePasse, BCrypt.gensalt());
    }

}
