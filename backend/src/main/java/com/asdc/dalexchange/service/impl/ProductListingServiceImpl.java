package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.service.ProductListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductListingServiceImpl implements ProductListingService {

    @Autowired
    private ProductRepository productRepository;

    public ProductListingServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return StreamSupport.stream(productRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }
}
