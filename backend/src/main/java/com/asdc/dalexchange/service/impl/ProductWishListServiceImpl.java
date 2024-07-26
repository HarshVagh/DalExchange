package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.PurchaseProductDTO;
import com.asdc.dalexchange.dto.SavedProductDTO;
import com.asdc.dalexchange.mappers.impl.PurchaseProductMapperImpl;
import com.asdc.dalexchange.mappers.impl.SavedProductMapperImpl;
import com.asdc.dalexchange.model.OrderDetails;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductWishlist;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.OrderRepository;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.repository.ProductWishlistRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.ProductWishlistService;
import com.asdc.dalexchange.specifications.ProductWishlistSpecification;
import com.asdc.dalexchange.util.AuthUtil;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductWishListServiceImpl implements ProductWishlistService {

    private final ProductWishlistRepository productWishlistRepository;

    private final UserRepository userRepository;

    private final SavedProductMapperImpl savedProductMapper;

    public final OrderRepository orderRepository;

    private final PurchaseProductMapperImpl purchaseProductMapper;

    private final ProductRepository productRepository;

    @Transactional
    public boolean markProductAsFavorite(long productId) {
        Long userId = AuthUtil.getCurrentUserId(userRepository);
        log.info("markProductAsFavorite called with productId: {} and userId: {}", productId, userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", userId);
                    return new ResourceNotFoundException("User not found with id " + userId);
                });
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", productId);
                    return new ResourceNotFoundException("Product not found with id " + productId);
                });

        Specification<ProductWishlist> spec = ProductWishlistSpecification.byUserIdAndProductId(userId, productId);
        List<ProductWishlist> existingWishlistItems = productWishlistRepository.findAll(spec);

        if (!existingWishlistItems.isEmpty()) {
            log.info("ProductId: {} is already in the wishlist. Removing it.", productId);
            productWishlistRepository.deleteAll(existingWishlistItems);
            return false;
        } else {
            log.info("Adding productId: {} to the wishlist.", productId);
            ProductWishlist productWishlist = new ProductWishlist();
            productWishlist.setUserId(user);
            productWishlist.setProductId(product);
            productWishlistRepository.save(productWishlist);
            return true;
        }
    }

    @Override
    public List<SavedProductDTO> getAllSavedProducts() {
        Long userId = AuthUtil.getCurrentUserId(userRepository);
        log.info("getAllSavedProducts called for userId: {}", userId);

        Specification<ProductWishlist> spec = ProductWishlistSpecification.byUserId(userId);
        List<ProductWishlist> allWishlistedProducts = productWishlistRepository.findAll(spec);

        List<Long> productIds = allWishlistedProducts.stream()
                .map(ProductWishlist::getProductId)
                .map(Product::getProductId)
                .collect(Collectors.toList());

        log.debug("Product IDs for wishlisted products: {}", productIds);

        List<Product> allSavedProducts = productRepository.findByProductIdIn(productIds);

        return allSavedProducts.stream()
                .map(savedProductMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseProductDTO> getAllPurchasedProduct() {
        Long userId = AuthUtil.getCurrentUserId(userRepository);
        log.info("getAllPurchasedProduct called for userId: {}", userId);

        List<OrderDetails> orderDetailsList = orderRepository.findByBuyerUserId(userId);
        return orderDetailsList.stream()
                .map(purchaseProductMapper::mapTo)
                .collect(Collectors.toList());
    }

    public boolean checkProductIsFavoriteByGivenUser(long productId) {
        Long userId = AuthUtil.getCurrentUserId(userRepository);
        log.info("checkProductIsFavoriteByGivenUser called with productId: {} and userId: {}", productId, userId);

        Specification<ProductWishlist> spec = ProductWishlistSpecification.byUserIdAndProductId(userId, productId);
        long count = productWishlistRepository.count(spec);
        boolean isFavorite = count > 0;

        log.debug("ProductId: {} favorite status for userId: {} is {}", productId, userId, isFavorite);
        return isFavorite;
    }
}
