package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.PaginatedResponse;
import com.asdc.dalexchange.dto.ProductListingDTO;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.service.ProductListingService;
import com.asdc.dalexchange.mappers.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
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
    public PaginatedResponse<ProductListingDTO> getProductListing(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) List<String> conditions,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        log.info("get products_listing api endpoint called!");
        log.info("get products_listing params: { " +
                "page: {}, " +
                "size: {}, " +
                "search: {}, " +
                "categories: {}, " +
                "conditions: {}, " +
                "minPrice: {}, " +
                "maxPrice: {}, }",
                page, size, search, categories, conditions, minPrice, maxPrice);

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productListingService.findByCriteria(pageable, search, categories, conditions, minPrice, maxPrice);

        List<ProductListingDTO> content = productPage.getContent().stream()
                .map(productListingMapper::mapTo)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                content,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages()
        );
    }
}
