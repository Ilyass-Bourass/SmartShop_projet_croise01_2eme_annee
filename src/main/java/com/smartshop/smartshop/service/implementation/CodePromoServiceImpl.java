package com.smartshop.smartshop.service.implementation;


import com.smartshop.smartshop.dto.codePromo.RequestCodePromoDTO;
import com.smartshop.smartshop.dto.codePromo.ResponseCodePromoDTO;
import com.smartshop.smartshop.entity.CodePromo;
import com.smartshop.smartshop.exception.DuplicateResourceException;
import com.smartshop.smartshop.exception.ExceptionConflit;
import com.smartshop.smartshop.mapper.CodePromo.CodePromoMapper;
import com.smartshop.smartshop.repository.CodePromoRepository;
import com.smartshop.smartshop.service.CodePromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodePromoServiceImpl implements CodePromoService {
    private final CodePromoRepository codePromoRepository;
    private final CodePromoMapper codePromoMapper;

    @Override
    public ResponseCodePromoDTO createCodePromo(RequestCodePromoDTO requestCodePromoDTO) {

        if(codePromoRepository.findByCodePromo(requestCodePromoDTO.getCodePromo()) != null){
            throw new ExceptionConflit("le code promo existe déja");
        }

        CodePromo codePromo =codePromoMapper.toEntiy(requestCodePromoDTO);
        CodePromo savedCodePromo = codePromoRepository.save(codePromo);
        return codePromoMapper.toResponseCodePromoDTO(savedCodePromo);
    }

    @Override
    public List<ResponseCodePromoDTO> getAllCodePromos() {
        return List.of();
    }

    @Override
    public ResponseCodePromoDTO getCodePromoById(Long id) {
        return null;
    }

    @Override
    public ResponseCodePromoDTO changerEtatCodePromo(Long id, Boolean etat) {
        return null;
    }

    @Override
    public String deleteCodePromo(Long id) {
        return "";
    }
}
