package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.ProductWishlistDTO;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductWishlist;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductWishlistService {
    ProductWishlist markProductAsFavorite(long userid, long productid);

    public List<Product> getProductIdsByUserId(Long userId);

//    public boolean checkProductIsFavoriteByGivenUser(long userId, long productId);
}
