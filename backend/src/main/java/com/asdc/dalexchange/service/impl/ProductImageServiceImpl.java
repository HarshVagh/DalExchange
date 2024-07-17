package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.model.ProductImage;
import com.asdc.dalexchange.repository.ProductImageRepository;
import com.asdc.dalexchange.service.ProductImageService;
import com.asdc.dalexchange.specifications.ProductImageSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<String> getProductAllImages(Long productId) {
        List<ProductImage> productImages = productImageRepository.findAll(ProductImageSpecification.byProductId(productId));
        return productImages.stream()
                .map(ProductImage::getImageUrl)
                .toList();
    }
}

