package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    List<Seller> findByUserUserId(Long userId);
    //List<Seller> findByUserId(Long userId);
}
