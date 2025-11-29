package com.smartshop.smartshop.repository;

import com.smartshop.smartshop.entity.CodePromo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;


@RestController
public interface CodePromoRepository extends JpaRepository<CodePromo,Long> {
    CodePromo findByCodePromo(String codePromo);
}
