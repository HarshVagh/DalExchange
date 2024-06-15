package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

}
