package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    @Query("SELECT pi.imageUrl FROM ProductImage pi WHERE pi.product.productId = :productId")
    List<String> findImageUrlsByProductIdWithMultipleImages(@Param("productId") Long productId);

}
