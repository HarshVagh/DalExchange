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
import com.asdc.dalexchange.util.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductWishListServiceImpl implements ProductWishlistService {

    @Autowired
    private ProductWishlistRepository productWishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SavedProductMapperImpl savedProductMapper;

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    private PurchaseProductMapperImpl purchaseProductMapper;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public boolean markProductAsFavorite(long userId, long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));

        Specification<ProductWishlist> spec = ProductWishlistSpecification.byUserIdAndProductId(userId, productId);
        List<ProductWishlist> existingWishlistItems = productWishlistRepository.findAll(spec);

        if (!existingWishlistItems.isEmpty()) {
            productWishlistRepository.deleteAll(existingWishlistItems);
            return false;
        } else {
            ProductWishlist productWishlist = new ProductWishlist();
            productWishlist.setUserId(user);
            productWishlist.setProductId(product);
            productWishlistRepository.save(productWishlist);
            return true;
        }
    }


    @Override
    public List<SavedProductDTO> getAllSavedProducts(Long userId) {
        Specification<ProductWishlist> spec = ProductWishlistSpecification.byUserId(userId);
        List<ProductWishlist> allWishlistedProducts = productWishlistRepository.findAll(spec);

        List<Long> productIds = allWishlistedProducts.stream()
                .map(ProductWishlist::getProductId)
                .map(Product::getProductId)
                .collect(Collectors.toList());

        List<Product> allSavedProducts = productRepository.findByProductIdIn(productIds);

        return allSavedProducts.stream()
                .map(savedProductMapper::mapTo)
                .collect(Collectors.toList());
    }



    @Override
    public List<PurchaseProductDTO> getAllPurchasedProduct(Long userid) {
        List<OrderDetails> orderDetailsList = orderRepository.findByBuyerUserId(userid);
        return orderDetailsList.stream()
                .map(purchaseProductMapper::mapTo)
                .collect(Collectors.toList());

    }

    public boolean checkProductIsFavoriteByGivenUser(long userId, long productId) {
        Specification<ProductWishlist> spec = ProductWishlistSpecification.byUserIdAndProductId(userId, productId);
        long count = productWishlistRepository.count(spec);
        return count > 0;
    }


}
