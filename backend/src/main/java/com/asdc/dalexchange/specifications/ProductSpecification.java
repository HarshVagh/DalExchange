package com.asdc.dalexchange.specifications;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import com.asdc.dalexchange.model.Product;

import java.util.List;

public class ProductSpecification {

    public static Specification<Product> hasCategory(List<String> categories) {
        return (root, query, criteriaBuilder) -> {
            if (categories == null || categories.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Object, Object> categoryJoin = root.join("category");
            return categoryJoin.get("name").in(categories);
        };
    }

    public static Specification<Product> bySellerUserId(Long userId) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("seller").get("userId"), userId);
            }
        };
    }

    public static Specification<Product> hasCondition(List<String> conditions) {
        return (root, query, criteriaBuilder) -> {
            if (conditions == null || conditions.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("productCondition").in(conditions);
        };
    }

    public static Specification<Product> hasPriceBetween(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (minPrice != null && minPrice >= 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null && maxPrice >= 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            return predicate;
        };
    }

    public static Specification<Product> hasTitleOrDescriptionContaining(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Predicate titlePredicate = criteriaBuilder.like(root.get("title"), "%" + search + "%");
            Predicate descriptionPredicate = criteriaBuilder.like(root.get("description"), "%" + search + "%");
            return criteriaBuilder.or(titlePredicate, descriptionPredicate);
        };
    }
}
