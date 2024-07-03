package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductWishlist;
import com.asdc.dalexchange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductWishlistRepository extends JpaRepository<ProductWishlist, Long> {

    ProductWishlist findByUserIdAndProductId(User user, Product product);

    /*@Query("SELECT p FROM Product p JOIN p.wishlist w WHERE w.userId = :userId")
    List<Product> findProductsByUserId(Long userId);*/

    List<ProductWishlist> findByUserIdUserId(Long userId);

    @Query("SELECT COUNT(*) FROM ProductWishlist WHERE productId = productId and userId = userId")
    int existsByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

}
