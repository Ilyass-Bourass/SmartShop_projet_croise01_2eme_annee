package com.smartshop.smartshop.dto.client;

import com.smartshop.smartshop.entity.Commande;
import com.smartshop.smartshop.enums.NiveauFidelite;
import com.smartshop.smartshop.enums.RoleUtilisateur;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class RequestClient {

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;


    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 5, message = "Le mot de passe doit contenir au moins 5 caractères")
    private String motDePasse;

    private RoleUtilisateur role;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le téléphone est obligatoire")
    private String telephone;

    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;

    private NiveauFidelite niveauFidelite;

    private Integer nombreTotalCommandes;

    private Double montantTotalCumule;

    private LocalDateTime datePremiereCommande;

    private LocalDateTime dateDerniereCommande;

}
