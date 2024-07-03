package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.SoldItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<SoldItem> findBySellerUserId(Long userId);

    List<Product> findByProductIdIn(List<Long> productIds);
}
