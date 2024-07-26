package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.model.ProductCategory;
import com.asdc.dalexchange.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping(path = "/product_categories")
    public List<String> getProductCategories() {
        log.info("get product_categories api endpoint called");
        return productCategoryService.findAll();
    }

    @GetMapping(path = "/categories")
    public List<ProductCategory> getCategories() {
        return productCategoryService.findAllCategories();
    }
}
