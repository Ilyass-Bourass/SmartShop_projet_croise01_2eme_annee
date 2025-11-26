package com.smartshop.smartshop.specifications;

import com.smartshop.smartshop.entity.Produit;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationProduit {
    public static Specification<Produit> isExiste(Boolean isExist) {
        return (root,query,cb) -> isExist == null ? null :cb.equal(root.get("isExiste"), isExist);
    }
}
