package com.smartshop.smartshop.controller;


import com.smartshop.smartshop.dto.codePromo.RequestCodePromoDTO;
import com.smartshop.smartshop.dto.codePromo.ResponseCodePromoDTO;
import com.smartshop.smartshop.mapper.CodePromo.CodePromoMapper;
import com.smartshop.smartshop.service.CodePromoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<ResponseCodePromoDTO>> getAllCodePromos() {
        List<ResponseCodePromoDTO> responseCodePromoDTOS=codePromoService.getAllCodePromos();
        return new ResponseEntity<>(responseCodePromoDTOS,HttpStatus.OK);
    }

    @GetMapping("/{id}" )
    public ResponseEntity<ResponseCodePromoDTO> getCodePromoById(@PathVariable String id) {
        ResponseCodePromoDTO responseCodePromoDTO=codePromoService.getCodePromoById(Long.parseLong(id));
        return new ResponseEntity<>(responseCodePromoDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{id}" )
    public  ResponseEntity<String> deleteCodePromoById(@PathVariable String id) {
        String response =codePromoService.deleteCodePromo(Long.parseLong(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/changerEtatCodepromo/{id}")
    public ResponseEntity<String> changerEtatCodePromo(@PathVariable Long id,@RequestParam Boolean etat) {
        String response= codePromoService.changerEtatCodePromo(id,etat);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
