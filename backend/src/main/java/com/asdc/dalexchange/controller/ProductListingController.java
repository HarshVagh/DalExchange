package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.PaginatedResponse;
import com.asdc.dalexchange.dto.ProductListingDTO;
import com.asdc.dalexchange.service.ProductListingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class ProductListingController {

    private ProductListingService productListingService;

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
        Page<ProductListingDTO> productPage = productListingService.findByCriteria(pageable, search, categories, conditions, minPrice, maxPrice);

        return new PaginatedResponse<>(
                productPage.getContent(),
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages()
        );
    }
}
