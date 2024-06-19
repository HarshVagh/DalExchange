package com.asdc.dalexchange.service;

import com.asdc.dalexchange.model.Product;

import java.util.List;

public interface ProductListingService {
    List<Product> findAll();
}
