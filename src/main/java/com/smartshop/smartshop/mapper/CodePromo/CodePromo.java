git package com.smartshop.smartshop.mapper.CodePromo;

import com.smartshop.smartshop.dto.codePromo.ResponseCodePromoDTO;
import com.smartshop.smartshop.dto.commande.RequestCommandeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CodePromo {
    @Mapping(target = "id", ignore = true)
    CodePromo toEntiy(RequestCommandeDTO requestCommandeDTO);
    ResponseCodePromoDTO toResponseCodePromoDTO(CodePromo codePromo);
}
