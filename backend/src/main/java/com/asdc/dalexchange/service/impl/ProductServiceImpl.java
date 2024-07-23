package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.AddProductDTO;
import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.dto.ProductModerationDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductCategory;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.ProductCategoryRepository;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.ProductService;
import com.asdc.dalexchange.util.AuthUtil;
import com.asdc.dalexchange.util.CloudinaryUtil;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper<Product, ProductDTO> productMapper;

    @Autowired
    private Mapper<Product, ProductModerationDTO> productModerationMapper;

    @Autowired
    private CloudinaryUtil cloudinaryUtil;

    @Override
    public ProductDTO getProductById(Long productId) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
        return productMapper.mapTo(product);
    }

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
    public void unlistProduct(Long productId, boolean unlisted) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setUnlisted(unlisted);
        productRepository.save(product);
    }

    public Product addProduct(AddProductDTO addProductDTO, ProductCategory category, MultipartFile file) {
        Product product = new Product();
        String imageURL = cloudinaryUtil.uploadImage(file);
        User seller = AuthUtil.getCurrentUser(userRepository);

        product.setTitle(addProductDTO.getTitle());
        product.setDescription(addProductDTO.getDescription());
        product.setPrice(addProductDTO.getPrice());
        product.setCategory(category);
        product.setProductCondition(addProductDTO.getProductCondition());
        product.setUseDuration(addProductDTO.getUseDuration());
        product.setShippingType(addProductDTO.getShippingType());
        product.setQuantityAvailable(addProductDTO.getQuantityAvailable());
        product.setSeller(seller);
        product.setImagePath(imageURL);
        product.setCreatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    public ProductCategory getCategoryById(Long categoryId) {
        return productCategoryRepository.findById(categoryId).orElse(null);
    }

    public Product getProductByID(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public ProductModerationDTO getProductByIdForModeration(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
        return productModerationMapper.mapTo(product);
    }

}

