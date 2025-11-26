package com.smartshop.smartshop.repository;

import com.smartshop.smartshop.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Produit findByNom(String nom);
}
