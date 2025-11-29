package com.smartshop.smartshop.mapper.CodePromo;

import com.smartshop.smartshop.dto.codePromo.RequestCodePromoDTO;
import com.smartshop.smartshop.dto.codePromo.ResponseCodePromoDTO;
import com.smartshop.smartshop.dto.commande.RequestCommandeDTO;
import com.smartshop.smartshop.entity.CodePromo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CodePromoMapper {
    @Mapping(target = "id", ignore = true)
    CodePromo toEntiy(RequestCodePromoDTO requestCodePromoDTO);
    ResponseCodePromoDTO toResponseCodePromoDTO(CodePromo codePromo);
}
