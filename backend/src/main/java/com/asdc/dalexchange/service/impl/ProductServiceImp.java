package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.service.ProductService;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Mapper<Product, ProductDTO> productMapper; // Inject the mapper

    @Override
    public ProductDTO getProductById(Long productId) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
        return productMapper.mapTo(product);
    }

}
