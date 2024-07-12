package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductWishlist;
import com.asdc.dalexchange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductWishlistRepository extends JpaRepository<ProductWishlist, Long>, JpaSpecificationExecutor<ProductWishlist> {

       ProductWishlist findByUserIdAndProductId(User user, Product product);

      // boolean deleteByUserIdAndProductId(Long user, Long product);
    

     List<ProductWishlist> findByUserIdUserId(Long userId);


}
