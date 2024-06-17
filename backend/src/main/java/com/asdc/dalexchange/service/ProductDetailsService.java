package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.ProductDetailsDTO;

public interface ProductDetailsService {

    // get the product all the details of th given product
    public ProductDetailsDTO DetailsOfProduct(Long productId);

}
