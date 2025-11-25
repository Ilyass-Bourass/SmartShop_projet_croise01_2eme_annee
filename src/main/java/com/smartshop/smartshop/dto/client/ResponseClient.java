package com.smartshop.smartshop.dto.client;

import com.smartshop.smartshop.enums.NiveauFidelite;
import com.smartshop.smartshop.enums.RoleUtilisateur;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResponseClient {
    private Long id;

    private String email;

    private RoleUtilisateur role;

    private String nom;

    private String telephone;

    private String adresse;

    private NiveauFidelite niveauFidelite;

    private Integer nombreTotalCommandes;

    private Double montantTotalCumule;

    private LocalDateTime datePremiereCommande;

    private LocalDateTime dateDerniereCommande;
}
