package com.asdc.dalexchange.specification;
import com.asdc.dalexchange.model.ProductImage;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;


public class ProductImageSpecification {

    public static Specification<ProductImage> byProductId(Long productId) {
        return new Specification<ProductImage>() {
            @Override
            public Predicate toPredicate(Root<ProductImage> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("product").get("productId"), productId);
            }
        };
    }
}
