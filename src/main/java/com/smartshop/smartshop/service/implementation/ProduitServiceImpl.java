package com.smartshop.smartshop.service.implementation;

import com.smartshop.smartshop.dto.produit.RequestProduitDTO;
import com.smartshop.smartshop.dto.produit.ResponseProduitDTO;
import com.smartshop.smartshop.entity.Produit;
import com.smartshop.smartshop.exception.DuplicateResourceException;
import com.smartshop.smartshop.mapper.produit.ProduitMapper;
import com.smartshop.smartshop.repository.ProduitRepository;
import com.smartshop.smartshop.service.ProduitService;
import com.smartshop.smartshop.specifications.SpecificationProduit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class ProduitServiceImpl implements ProduitService {
    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;

    @Override
    public ResponseProduitDTO createProduit(RequestProduitDTO requestProduitDTO) {
        Produit produitExisting = produitRepository.findByNom(requestProduitDTO.getNom());
        if (produitExisting != null) {
            throw new DuplicateResourceException(List.of("Le produit avec le nom '" + requestProduitDTO.getNom() + "' existe déjà. tu peut faire une mise à jour au lieu de créer un nouveau produit."));
        }
        Produit produit = produitMapper.toProduit(requestProduitDTO);
        produit.setStockDisponible(requestProduitDTO.getQuantite());
        Produit savedProduit = produitRepository.save(produit);
        ResponseProduitDTO responseProduitDTO = produitMapper.toResponseProduitDTO(savedProduit);
        return responseProduitDTO;
    }

    @Override
    public List<ResponseProduitDTO> findAll(int page,int size,Boolean isExiste) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Produit> sp = SpecificationProduit.isExiste(isExiste);

        List<ResponseProduitDTO> produits = produitRepository.findAll(sp,pageable)
                .stream()
                .map(produitMapper::toResponseProduitDTO)
                .toList();
        return produits;
    }

    @Override
    public String deleteProduit(Long id) {
        return "";
    }

    @Override
    public ResponseProduitDTO updateProduit(RequestProduitDTO requestProduitDTO) {
        return null;
    }
}
