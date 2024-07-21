package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.Payment;
import com.asdc.dalexchange.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
