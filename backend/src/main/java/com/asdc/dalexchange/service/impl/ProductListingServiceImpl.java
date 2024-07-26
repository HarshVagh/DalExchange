package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.ProductListingDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductImage;
import com.asdc.dalexchange.repository.ProductImageRepository;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.service.ProductListingService;
import com.asdc.dalexchange.specifications.ProductImageSpecification;
import com.asdc.dalexchange.specifications.ProductSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductListingServiceImpl implements ProductListingService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final Mapper<Product, ProductListingDTO> productListingMapper;

    @Autowired
    public ProductListingServiceImpl(
            ProductRepository productRepository,
            ProductImageRepository productImageRepository,
            Mapper<Product, ProductListingDTO> productListingMapper) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.productListingMapper = productListingMapper;
    }


    @Override
    public Page<ProductListingDTO> findByCriteria(Pageable pageable, String search, List<String> categories, List<String> conditions, Double minPrice, Double maxPrice) {
        log.info("Find by Criteria call started in the ProductListingServiceImpl");
        Specification<Product> spec = Specification.where(ProductSpecification.hasTitleOrDescriptionContaining(search))
                .and(ProductSpecification.hasCategory(categories))
                .and(ProductSpecification.hasCondition(conditions))
                .and(ProductSpecification.hasPriceBetween(minPrice, maxPrice))
                .and(ProductSpecification.isNotUnlisted());
        Page<Product> productPage = productRepository.findAll(spec, pageable);

        List<ProductListingDTO> productListingDTOs = productPage.getContent().stream().map(product -> {
            ProductListingDTO productListingDTO = productListingMapper.mapTo(product);
            Specification<ProductImage> imageSpec = ProductImageSpecification.byProductId(productListingDTO.getProductId());
            List<ProductImage> productImages = productImageRepository.findAll(imageSpec);
            if (!productImages.isEmpty()) {
                productListingDTO.setImageUrl(productImages.get(0).getImageUrl());
            }
            return productListingDTO;
        }).collect(Collectors.toList());

        return new PageImpl<>(productListingDTOs, pageable, productPage.getTotalElements());
    }
}
