package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.model.ProductCategory;
import com.asdc.dalexchange.repository.ProductCategoryRepository;
import com.asdc.dalexchange.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public List<String> findAll() {
        return productCategoryRepository.findAll()
                .stream()
                .map(ProductCategory::getName)
                .toList();
    }
}
