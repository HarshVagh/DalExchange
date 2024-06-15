package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.ProductWishlistDTO;
import com.asdc.dalexchange.model.ProductWishlist;
import org.springframework.stereotype.Service;

@Service
public interface ProductWishlistService {
    ProductWishlist markProductAsFavorite(long userid, long productid);
}
