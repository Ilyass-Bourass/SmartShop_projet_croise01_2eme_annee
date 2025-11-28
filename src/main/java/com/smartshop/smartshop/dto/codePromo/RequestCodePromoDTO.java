package com.smartshop.smartshop.dto.codePromo;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RequestCodePromoDTO {
    @NotBlank(message = "Le code promo ne peut pas être vide")
    @Pattern(regexp = "^[A-Z0-9]{4}$", message = "Le code promo doit contenir exactement 4 caractères majuscules ou chiffres")
    private String codePromo;
}
