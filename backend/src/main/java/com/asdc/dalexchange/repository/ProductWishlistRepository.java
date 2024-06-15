package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductWishlist;
import com.asdc.dalexchange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductWishlistRepository extends JpaRepository<ProductWishlist, Long> {
    ProductWishlist findByUserIdAndProductId(User user, Product product);
}
