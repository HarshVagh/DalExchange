package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.service.ProductCategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class ProductCategoryController {

    private ProductCategoryService productCategoryService;

    @GetMapping(path = "/product_categories")
    public List<String> getProductCategories() {
        log.info("get product_categories api endpoint called");
        return productCategoryService.findAll();
    }

}
