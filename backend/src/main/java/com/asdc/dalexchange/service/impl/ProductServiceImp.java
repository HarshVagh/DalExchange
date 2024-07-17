package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.dto.ProductModerationDTO;
import com.asdc.dalexchange.dto.UserDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductCategory;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.ProductCategoryRepository;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.service.ProductService;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private Mapper<Product, ProductDTO> productMapper;

    @Autowired
    private Mapper<Product, ProductModerationDTO> productModerationMapper;

    @Override
    public ProductDTO getProductById(Long productId) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
        return productMapper.mapTo(product);
    }

    // Product Moderation - prashanth
    public List<ProductModerationDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productModerationMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductModerationDTO updateProduct(Long productId, ProductModerationDTO updatedProductDetails) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (updatedProductDetails.getTitle() != null) {
            product.setTitle(updatedProductDetails.getTitle());
        }
        if (updatedProductDetails.getDescription() != null) {
            product.setDescription(updatedProductDetails.getDescription());
        }
        if (updatedProductDetails.getPrice() != 0) {
            product.setPrice(updatedProductDetails.getPrice());
        }
        if (updatedProductDetails.getCategory() != null) {
            ProductCategory category = productCategoryRepository.findById(updatedProductDetails.getCategory().getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }
        if (updatedProductDetails.getProductCondition() != null) {
            product.setProductCondition(updatedProductDetails.getProductCondition());
        }
        if (updatedProductDetails.getUseDuration() != null) {
            product.setUseDuration(updatedProductDetails.getUseDuration());
        }
        if (updatedProductDetails.getShippingType() != null) {
            product.setShippingType(updatedProductDetails.getShippingType());
        }
        if (updatedProductDetails.getQuantityAvailable() != null) {
            product.setQuantityAvailable(updatedProductDetails.getQuantityAvailable());
        }

        return productModerationMapper.mapTo(productRepository.save(product));
    }

    @Transactional
    public void unlistProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setUnlisted(true);
        productRepository.save(product);
    }

}
