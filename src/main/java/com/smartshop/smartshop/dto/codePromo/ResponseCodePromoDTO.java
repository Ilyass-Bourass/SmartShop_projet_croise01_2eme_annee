package com.smartshop.smartshop.dto.codePromo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResponseCodePromoDTO {

    private Long id;
    private String codePromo;
    private Boolean isActif;
    private LocalDateTime dateCreation;
}
