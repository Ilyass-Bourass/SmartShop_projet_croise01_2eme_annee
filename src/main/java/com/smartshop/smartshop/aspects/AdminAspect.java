package com.smartshop.smartshop.aspects;


import com.smartshop.smartshop.dto.auth.ResponseLogin;
import com.smartshop.smartshop.entity.Utilisateur;
import com.smartshop.smartshop.enums.RoleUtilisateur;
import com.smartshop.smartshop.exception.ForbiddenException;
import com.smartshop.smartshop.exception.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import com.smartshop.smartshop.annotations.AdminOnly;

@Aspect
@Component
@RequiredArgsConstructor

public class AdminAspect {
    private final HttpSession httpSession;

    @Before("@annotation(com.smartshop.smartshop.annotations.AdminOnly)")

    public void checkAdminRole() {
        ResponseLogin currentUser = (ResponseLogin) httpSession.getAttribute("UTILISATEUR_SESSION");

        if (currentUser == null) {
            throw new UnauthorizedException("Vous devez être connecté pour accéder à cette ressource");
        }

        if (!currentUser.getRole().equals(RoleUtilisateur.ADMIN)) {
            throw new ForbiddenException("Accès refusé : cette ressource est réservée aux administrateurs");
        }
    }


}
