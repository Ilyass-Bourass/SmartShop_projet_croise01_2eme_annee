package com.smartshop.smartshop.service;


import com.smartshop.smartshop.dto.auth.RequestLogin;
import com.smartshop.smartshop.dto.auth.ResponseLogin;
import com.smartshop.smartshop.entity.Utilisateur;
import com.smartshop.smartshop.mapper.utilsateur.UtilisateurMapper;
import com.smartshop.smartshop.repository.UtilsateurRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UtilsateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;
    public ResponseLogin login(RequestLogin requestLogin){
        Utilisateur utilisateur = utilisateurRepository.findByEmail(requestLogin.getEmail());
        if(utilisateur == null || !BCrypt.checkpw(requestLogin.getPassword(), utilisateur.getMotDePasse())){
            throw new IllegalArgumentException("email ou mot de passe incorrect");
        }

        return utilisateurMapper.toResponseLogin(utilisateur);
    }
}
