package com.asdc.dalexchange.service.imp;


import com.asdc.dalexchange.model.ProductImage;
import com.asdc.dalexchange.repository.ProductImageRepository;
import com.asdc.dalexchange.service.ProductImageService;
import com.asdc.dalexchange.specification.ProductImageSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImp implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public List<String> getProductAllImages(Long productId) {
        // Use the Specification to fetch images for the given productId
        List<ProductImage> productImages = productImageRepository.findAll(ProductImageSpecification.byProductId(productId));

        // Extract image URLs from the productImages list
        return productImages.stream()
                .map(ProductImage::getImageUrl)
                .toList(); // Requires Java 16+, use .collect(Collectors.toList()) for older versions
    }
}

