package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.PurchaseProductDTO;
import com.asdc.dalexchange.dto.SavedProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductWishlistService {
    boolean markProductAsFavorite(long userId, long productId);

    public List<PurchaseProductDTO> getAllPurchasedProduct(Long userid);

    public boolean checkProductIsFavoriteByGivenUser(long userId, long productId);

    public List<SavedProductDTO> getAllSavedProducts(Long userId);

}
