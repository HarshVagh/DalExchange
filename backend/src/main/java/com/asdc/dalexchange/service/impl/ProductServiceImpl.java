package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.AddProductDTO;
import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductCategory;
import com.asdc.dalexchange.repository.ProductCategoryRepository;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.service.ProductService;
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

@Service
public class ProductServiceImpl implements ProductService {

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


    // Product Moderation - prashanth
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product updateProduct(Long productId, Product updatedProductDetails) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (updatedProductDetails.getSeller() != null) {
            product.setSeller(updatedProductDetails.getSeller());
        }
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
            product.setCategory(updatedProductDetails.getCategory());
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

        return productRepository.save(product);
    }

    @Transactional
    public void unlistProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setUnlisted(true);
        productRepository.save(product);
    }

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    private static final String UPLOAD_DIR = "/Users/shivaniuppe/Desktop/dal-exchange/group09/backend/src/uploads/";

    public Product addProduct(AddProductDTO addProductDTO, ProductCategory category, MultipartFile file) throws IOException {

        Product product = new Product();
        product.setTitle(addProductDTO.getTitle());
        product.setDescription(addProductDTO.getDescription());
        product.setPrice(addProductDTO.getPrice());
        product.setCategory(category);
        product.setProductCondition(addProductDTO.getProductCondition());
        product.setUseDuration(addProductDTO.getUseDuration());
        product.setShippingType(addProductDTO.getShippingType());
        product.setQuantityAvailable(addProductDTO.getQuantityAvailable());
        if (file != null && !file.isEmpty()) {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + filename);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            product.setImagePath(filePath.toString());
        }
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public ProductCategory getCategoryById(Long categoryId) {
        return productCategoryRepository.findById(categoryId).orElse(null);
    }
    public Product getProductByID(Long id) {
        return productRepository.findById(id).orElse(null);
    }

}
