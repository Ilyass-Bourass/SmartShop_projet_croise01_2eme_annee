package com.smartshop.smartshop.mapper.utilsateur;


import com.smartshop.smartshop.dto.auth.ResponseLogin;
import com.smartshop.smartshop.entity.Utilisateur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {
    ResponseLogin toResponseLogin(Utilisateur utilisateur);
}
