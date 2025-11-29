package com.smartshop.smartshop.controller;


import com.smartshop.smartshop.dto.codePromo.RequestCodePromoDTO;
import com.smartshop.smartshop.dto.codePromo.ResponseCodePromoDTO;
import com.smartshop.smartshop.mapper.CodePromo.CodePromoMapper;
import com.smartshop.smartshop.service.CodePromoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/codepromos")
@RequiredArgsConstructor

public class CodePromoController {
    private final CodePromoMapper codePromoMapper;
    private final CodePromoService codePromoService;

    @PostMapping
    public ResponseEntity<ResponseCodePromoDTO> saveCodePromo(@Valid @RequestBody RequestCodePromoDTO requestCodePromoDTO) {
        ResponseCodePromoDTO responseCodePromoDTO = codePromoService.createCodePromo(requestCodePromoDTO);
        return new ResponseEntity<>(responseCodePromoDTO, HttpStatus.CREATED);
    }
}
