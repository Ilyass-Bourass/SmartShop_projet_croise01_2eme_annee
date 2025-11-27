package com.smartshop.smartshop.controller;


import com.smartshop.smartshop.dto.auth.RequestLogin;
import com.smartshop.smartshop.dto.auth.ResponseLogin;
import com.smartshop.smartshop.entity.Client;
import com.smartshop.smartshop.enums.RoleUtilisateur;
import com.smartshop.smartshop.exception.ResourceNotFoundException;
import com.smartshop.smartshop.mapper.client.ClientMapper;
import com.smartshop.smartshop.repository.ClientRepository;
import com.smartshop.smartshop.service.implementation.AuthServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")

public class AuthController {
    private final AuthServiceImpl authService;
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @PostMapping("/login")
    public ResponseEntity<Map<String, ResponseLogin>> login(@Valid @RequestBody  RequestLogin requestLogin, HttpSession session) {
        ResponseLogin utilisateur = authService.login(requestLogin);
        session.setAttribute("UTILISATEUR_SESSION", utilisateur);
        session.setAttribute("UTILISATEUR_SESSION_ID", session.getId());
        Map<String, ResponseLogin> map = new HashMap<>();
        map.put("la connexion effectué avec succée  session id : " + session.getId(), utilisateur);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        if(session.getAttribute("UTILISATEUR_SESSION") == null) {
            return new ResponseEntity<>("Aucun utilisateur connecté", HttpStatus.BAD_REQUEST);
        }
        session.invalidate();
        return new ResponseEntity<>("Déconnexion réussie", HttpStatus.OK);
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        ResponseLogin currentUser = (ResponseLogin) session.getAttribute("UTILISATEUR_SESSION");
        if (currentUser == null) {
            return new ResponseEntity<>("accun utilisateur connecté", HttpStatus.BAD_REQUEST);
        }
        if(currentUser.getRole().equals(RoleUtilisateur.CLIENT)){
            Client client = clientRepository.findByEmail(currentUser.getEmail()).orElseThrow(()->new ResourceNotFoundException("le client n'existe pas"));
            return new ResponseEntity<>(clientMapper.toResponseClient(client), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        }
    }
}
