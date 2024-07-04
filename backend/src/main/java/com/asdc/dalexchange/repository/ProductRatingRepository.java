package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.ProductRating;
import com.asdc.dalexchange.model.ProductRatingID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRatingRepository extends JpaRepository<ProductRating, ProductRatingID> {
    List<ProductRating> findByIdUserId(Long userId);;
}
