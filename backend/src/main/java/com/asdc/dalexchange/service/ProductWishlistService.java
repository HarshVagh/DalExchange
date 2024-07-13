package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.ProductWishlistDTO;
import com.asdc.dalexchange.dto.PurchaseProductDTO;
import com.asdc.dalexchange.dto.SavedProductDTO;
import com.asdc.dalexchange.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductWishlistService {
    boolean markProductAsFavorite(long userId, long productId);
     // ProductWishlist markProductAsFavorite(long userid, long productid);
    //public List<Product> getProductIdsByUserId(Long userId);

    public List<PurchaseProductDTO> GetallPurchasedProduct(Long userid);

    public boolean checkProductIsFavoriteByGivenUser(long userId, long productId);

      List<Product> getProductIdsByUserId(Long userId);

    public List<SavedProductDTO> GetAllsavedProduct(Long userId);

    
     public boolean removeProductFromFavorite(long userId, long productId)
}
