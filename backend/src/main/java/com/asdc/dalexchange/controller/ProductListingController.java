package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.ProductListingDTO;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.service.ProductListingService;
import com.asdc.dalexchange.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000/products")
@RestController
public class ProductListingController {

    @Autowired
    private ProductListingService productListingService;

    @Autowired
    private Mapper<Product, ProductListingDTO> productListingMapper;

    public ProductListingController(ProductListingService productListingService, Mapper<Product, ProductListingDTO> productListingMapper) {
        this.productListingService = productListingService;
        this.productListingMapper = productListingMapper;
    }

    @GetMapping(path = "/products_listing")
    public List<ProductListingDTO> getProductListing() {
        List<Product> products = productListingService.findAll();
        return products.stream()
                .map(productListingMapper::mapTo)
                .collect(Collectors.toList());
    }
}
