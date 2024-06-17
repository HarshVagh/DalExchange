package com.asdc.dalexchange.service.imp;

import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductWishlist;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.repository.ProductWishlistRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.ProductWishlistService;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductWishListServiceImp implements ProductWishlistService {

    @Autowired
    private ProductWishlistRepository productWishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private ModelMapper modelMapper;


    @Transactional
    public ProductWishlist markProductAsFavorite(long userId, long productId) {
        // Retrieve the User and Product entities using the instance of the repository
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));

        // Check if the product is already in the user's wishlist
        ProductWishlist existingWishlistItem = productWishlistRepository.findByUserIdAndProductId(user, product);
        if (existingWishlistItem != null) {
            // If it exists, remove it
            productWishlistRepository.delete(existingWishlistItem);
            return null; // Indicate that the item was removed
        } else {
            // If it doesn't exist, add it to the wishlist
            ProductWishlist productWishlist = new ProductWishlist();
            productWishlist.setUserId(user);
            productWishlist.setProductId(product);

            // Save the ProductWishlist entity
            return productWishlistRepository.save(productWishlist);
        }
    }
    }




