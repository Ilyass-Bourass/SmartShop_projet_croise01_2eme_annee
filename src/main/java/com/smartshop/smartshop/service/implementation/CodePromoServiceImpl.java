package com.smartshop.smartshop.service.implementation;


import com.smartshop.smartshop.dto.codePromo.RequestCodePromoDTO;
import com.smartshop.smartshop.dto.codePromo.ResponseCodePromoDTO;
import com.smartshop.smartshop.entity.CodePromo;
import com.smartshop.smartshop.exception.DuplicateResourceException;
import com.smartshop.smartshop.exception.ExceptionConflit;
import com.smartshop.smartshop.exception.ResourceNotFoundException;
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
        List<CodePromo> codePromos = codePromoRepository.findAll();
        return codePromos.stream().
                map(codePromoMapper::toResponseCodePromoDTO).toList();
    }

    @Override
    public ResponseCodePromoDTO getCodePromoById(Long id) {
        CodePromo codePromo = codePromoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le code promo de l id : " + id + " n'existe pas"));
        return codePromoMapper.toResponseCodePromoDTO(codePromo);
    }

    @Override
    public String changerEtatCodePromo(Long id, Boolean etat) {
        CodePromo codePromo = codePromoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le code promo de l id : " + id + " n'existe pas"));
        if(etat == codePromo.getIsActif()){
            return "le code promo est déjà dans cet état";
        }else {
            codePromo.setIsActif(etat);
            codePromoRepository.save(codePromo);
        return "nous avons changé l'état du code promo avec succès";
        }
    }

    @Override
    public String deleteCodePromo(Long id) {
        CodePromo codePromo = codePromoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le code promo de l id : " + id + " n'existe pas"));
        codePromoRepository.delete(codePromo);
        return "le code promo a été supprimé avec succès";
    }

}
