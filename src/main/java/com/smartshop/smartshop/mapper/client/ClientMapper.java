package com.smartshop.smartshop.mapper.client;


import com.smartshop.smartshop.dto.client.RequestClient;
import com.smartshop.smartshop.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mindrot.jbcrypt.BCrypt;

@Mapper(componentModel = "spring" )
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "motDePasse" , expression = "java(hashPassword(requestClient.getMotDePasse()))")
    Client toClient(RequestClient requestClient);
    default String hashPassword(String motDePasse) {
        return BCrypt.hashpw(motDePasse,BCrypt.gensalt());
    }
}
