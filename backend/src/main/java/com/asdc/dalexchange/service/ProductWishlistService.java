package com.asdc.dalexchange.service;

import com.asdc.dalexchange.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductWishlistService {
    boolean markProductAsFavorite(long userId, long productId);

    List<Product> getProductIdsByUserId(Long userId);
}
