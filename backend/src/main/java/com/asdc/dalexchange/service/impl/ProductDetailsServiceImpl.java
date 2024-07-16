package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductImage;
import com.asdc.dalexchange.model.ProductWishlist;
import com.asdc.dalexchange.repository.ProductImageRepository;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.repository.ProductWishlistRepository;
import com.asdc.dalexchange.specifications.ProductImageSpecification;
import com.asdc.dalexchange.specifications.ProductWishlistSpecification;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import com.asdc.dalexchange.service.ProductDetailsService;


@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;
    private final ProductWishlistRepository productWishlistRepository;
    private final Mapper<Product, ProductDetailsDTO> productDetailsMapper;

    @Autowired
    public ProductDetailsServiceImpl(ProductRepository productRepository,
                                     ProductImageRepository productImageRepository,
                                     ProductWishlistRepository productWishlistRepository,
                                     Mapper<Product, ProductDetailsDTO> productDetailsMapper) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.productWishlistRepository = productWishlistRepository;
        this.productDetailsMapper = productDetailsMapper;
    }

    @Override
    public ProductDetailsDTO getDetails(Long userId, Long productId) {
        // Get all the details of the product
        Product product = getProductById(productId);

        // Get all image URLs of the given product
        List<String> productImageUrls = getImageUrls(productId);

        // Map Product to ProductDetailsDTO
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.mapTo(product);

        // Set the image URLs to ProductDetailsDTO
        productDetailsDTO.setImageurl(productImageUrls);

        // Set the seller joining date and rating
        productDetailsDTO.setSellerId(product.getSeller().getUserId());
        productDetailsDTO.setSellerJoiningDate(product.getSeller().getJoinedAt());
        productDetailsDTO.setRating(product.getSeller().getSellerRating());

        // Set the favorite status
        productDetailsDTO.setFavorite(getFavoriteStatus(userId, productId));

        return productDetailsDTO;
    }

    @Override
    public Product getProductById(Long productId) {
        return this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
    }

    @Override

    public List<String> getImageUrls(Long productId) {
        Specification<ProductImage> spec = ProductImageSpecification.byProductId(productId);
        List<ProductImage> productImages = productImageRepository.findAll(spec);

        // Extract image URLs from the productImages list
        return productImages.stream()
                .map(ProductImage::getImageUrl)
                .toList();
    }

    @Override
    public boolean getFavoriteStatus(long userId, long productId) {
        Specification<ProductWishlist> spec = ProductWishlistSpecification.byUserIdAndProductId(userId, productId);
        long count = productWishlistRepository.count(spec);
        return count > 0;
    }
}
