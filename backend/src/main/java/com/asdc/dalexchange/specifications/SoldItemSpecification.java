package com.asdc.dalexchange.specifications;

import com.asdc.dalexchange.model.SoldItem;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class SoldItemSpecification {

    public static Specification<SoldItem> bySellerUserId(Long userId) {
        return new Specification<SoldItem>() {
            @Override
            public Predicate toPredicate(Root<SoldItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("product").get("seller").get("userId"), userId);
            }
        };
    }
}
