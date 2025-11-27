package com.smartshop.smartshop.repository;

import com.smartshop.smartshop.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement,Long> {
    Paiement findByNumeroPaiment(String nom);
}
