package com.smartshop.smartshop.service;


import com.smartshop.smartshop.dto.codePromo.RequestCodePromoDTO;
import com.smartshop.smartshop.dto.codePromo.ResponseCodePromoDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CodePromoService {
    ResponseCodePromoDTO createCodePromo(RequestCodePromoDTO requestCodePromoDTO);
    List<ResponseCodePromoDTO> getAllCodePromos();
    ResponseCodePromoDTO getCodePromoById(Long id);
    ResponseCodePromoDTO changerEtatCodePromo(Long id, Boolean etat);
    String deleteCodePromo(Long id);
}
