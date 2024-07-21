package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductImage;
import com.asdc.dalexchange.model.ProductWishlist;
import com.asdc.dalexchange.repository.ProductImageRepository;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.repository.ProductWishlistRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.ProductDetailsService;
import com.asdc.dalexchange.specifications.ProductImageSpecification;
import com.asdc.dalexchange.specifications.ProductWishlistSpecification;
import com.asdc.dalexchange.util.AuthUtil;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class ProductDetailsServiceImpl implements ProductDetailsService {


    private ProductRepository productRepository;
    private UserRepository userRepository;
    private ProductImageRepository productImageRepository;
    private final ProductWishlistRepository productWishlistRepository;
    private final Mapper<Product, ProductDetailsDTO> productDetailsMapper;

    public ProductDetailsServiceImpl(
            ProductRepository productRepository,
            UserRepository userRepository,
            ProductImageRepository productImageRepository,
            ProductWishlistRepository productWishlistRepository,
            Mapper<Product, ProductDetailsDTO> productDetailsMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productImageRepository = productImageRepository;
        this.productWishlistRepository = productWishlistRepository;
        this.productDetailsMapper = productDetailsMapper;
    }

    @Override
    public ProductDetailsDTO getDetails(Long productId) {

        Long userId = AuthUtil.getCurrentUserId(userRepository);


        // Get all the details of the product
        Product product = getProductById(productId);
        System.out.println("details of the product : " + product);

        // Get all image URLs of the given product
        List<String> productImageUrls = getImageUrls(productId);

        // Map Product to ProductDetailsDTO
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.mapTo(product);
        System.out.println("productDetailsDTO : " + productDetailsDTO);

        // Set the image URLs to ProductDetailsDTO
        productDetailsDTO.setImageurl(productImageUrls);

        productDetailsDTO .setCategory("Books");

        // Set the seller joining date and rating
        productDetailsDTO.setSellerId(product.getSeller().getUserId());
        productDetailsDTO.setSellerJoiningDate(product.getSeller().getJoinedAt());
        System.out.println("the Product details DTo " + productDetailsDTO);
        Double sellerRating = product.getSeller().getSellerRating();
        double rating = (sellerRating != null) ? sellerRating.doubleValue() : 0.0; // Default to 0.0 if null
        productDetailsDTO.setRating(rating);
        //productDetailsDTO.setRating(product.getSeller().getSellerRating());

        log.info("userId is : " + userId);
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
