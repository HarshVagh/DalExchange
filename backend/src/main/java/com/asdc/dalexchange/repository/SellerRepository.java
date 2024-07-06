package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
