package com.smartshop.smartshop.dto.auth;


import com.smartshop.smartshop.enums.RoleUtilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResponseLogin {
    private Long id;
    private String email;
    private RoleUtilisateur role;
}
