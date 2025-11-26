package com.smartshop.smartshop.config;


import com.smartshop.smartshop.entity.Utilisateur;
import com.smartshop.smartshop.enums.RoleUtilisateur;
import com.smartshop.smartshop.repository.UtilsateurRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class DatabaseSeeder implements CommandLineRunner {

    private final UtilsateurRepository utilisateurRepository;

    @Override

    public void run(String... args) throws Exception {

            if(utilisateurRepository.count() == 0) {

              Utilisateur user01= Utilisateur.builder()
                    .email("admin@gmail.com")
                    .motDePasse(BCrypt.hashpw("12345", BCrypt.gensalt()))
                    .role(RoleUtilisateur.ADMIN).
                    build();
              utilisateurRepository.save(user01);

            }
    }
}
