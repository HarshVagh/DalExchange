package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.model.Product;

import java.util.List;

public interface ProductDetailsService {

    // get the product all the details of th given product
    public ProductDetailsDTO getDetails(Long userid, Long productId);

    public  Product getProductById(Long productId);

    public List<String> getImageUrls(Long productId);

    public boolean getFavoriteStatus(long userId, long productId);


}
