package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.service.ProductListingService;
import com.asdc.dalexchange.specifications.ProductSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductListingServiceImpl implements ProductListingService {

    @Autowired
    private ProductRepository productRepository;

    public ProductListingServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    @Override
    public Page<Product> findByCriteria(Pageable pageable, String search, List<String> categories, List<String> conditions, Double minPrice, Double maxPrice) {
        log.info("Find by Criteria call started in the ProductListingServiceImpl");
        Specification<Product> spec = Specification.where(ProductSpecification.hasTitleOrDescriptionContaining(search))
                .and(ProductSpecification.hasCategory(categories))
                .and(ProductSpecification.hasCondition(conditions))
                .and(ProductSpecification.hasPriceBetween(minPrice, maxPrice));
        return productRepository.findAll(spec, pageable);
    }
}
