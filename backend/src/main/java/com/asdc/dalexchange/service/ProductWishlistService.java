package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.PurchaseProductDTO;
import com.asdc.dalexchange.dto.SavedProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductWishlistService {
    boolean markProductAsFavorite(long productId);

    public List<PurchaseProductDTO> getAllPurchasedProduct();

    public boolean checkProductIsFavoriteByGivenUser(long productId);

    public List<SavedProductDTO> getAllSavedProducts();

}
