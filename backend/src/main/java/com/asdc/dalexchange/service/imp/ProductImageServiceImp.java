package com.asdc.dalexchange.service.imp;

import com.asdc.dalexchange.repository.ProductImageRepository;
import com.asdc.dalexchange.service.ProductImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        // Use the repository instance correctly
        return productImageRepository.findImageUrlsByProductIdWithMultipleImages(productId);
    }


}
