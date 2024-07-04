package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping(path = "/product_categories")
    public List<String> getProductCategories() {
        return productCategoryService.findAll();
    }
}
