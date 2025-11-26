package com.smartshop.smartshop.service.implementation;


import com.smartshop.smartshop.dto.auth.RequestLogin;
import com.smartshop.smartshop.dto.auth.ResponseLogin;
import com.smartshop.smartshop.entity.Utilisateur;
import com.smartshop.smartshop.exception.IdentifiantsInvalidesException;
import com.smartshop.smartshop.mapper.utilsateur.UtilisateurMapper;
import com.smartshop.smartshop.repository.UtilsateurRepository;
import com.smartshop.smartshop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UtilsateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;

    @Override
    public ResponseLogin login(RequestLogin requestLogin){
        Utilisateur utilisateur = utilisateurRepository.findByEmail(requestLogin.getEmail());
        if(utilisateur == null || !BCrypt.checkpw(requestLogin.getPassword(), utilisateur.getMotDePasse())){
            throw new IdentifiantsInvalidesException("email ou mot de passe incorrect");
        }

        return utilisateurMapper.toResponseLogin(utilisateur);
    }
}
