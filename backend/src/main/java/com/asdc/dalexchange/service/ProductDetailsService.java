package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.model.Product;

import java.util.List;

public interface ProductDetailsService {

    // get the product all the details of th given product
    ProductDetailsDTO getDetails(Long productId);

    Product getProductById(Long productId);

    List<String> getImageUrls(Long productId);

    boolean getFavoriteStatus(long userId, long productId);


}
