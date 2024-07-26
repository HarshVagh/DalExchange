package com.asdc.dalexchange.specifications;

import com.asdc.dalexchange.model.Payment;
import org.springframework.data.jpa.domain.Specification;

public class PaymentSpecification {

    public static Specification<Payment> hasProductId(Long orderId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(
                    root.get("order").get("orderId"),
                    orderId
            );
        };
    }

}
