package com.asdc.dalexchange.service.imp;

import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductWishlist;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.repository.ProductWishlistRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.ProductService;
import com.asdc.dalexchange.service.ProductWishlistService;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.AllPermission;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductWishListServiceImp implements ProductWishlistService {

    @Autowired
    private ProductWishlistRepository productWishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private ModelMapper modelMapper;


    // add to wishlist and remove to wishlist
    // @Autowired
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
            System.out.println("SDsfsfsdf");
            // If it exists, remove it
            productWishlistRepository.delete(existingWishlistItem);
            return null; // Indicate that the item was removed
        } else {
            System.out.println("SDsfsfsdf");
            // If it doesn't exist, add it to the wishlist
            ProductWishlist productWishlist = new ProductWishlist();
            productWishlist.setUserId(user);
            productWishlist.setProductId(product);

            // Save the ProductWishlist entity
            return productWishlistRepository.save(productWishlist);
        }
    }

    // get the all the wishlisted product of the given user
    public List<Product> getProductIdsByUserId(Long userId) {
        List<ProductWishlist> allWishlistedProducts = productWishlistRepository.findByUserIdUserId(userId);
        List<Long> productIds = allWishlistedProducts.stream()
                .map(ProductWishlist::getProductId)
                .map(Product::getProductId)
                .collect(Collectors.toList());
        List<Product> products = productRepository.findByProductIdIn(productIds);
        return products;
    }

    public boolean checkProductIsFavoriteByGivenUser(long userId, long productId) {
        int count = productWishlistRepository.existsByUserIdAndProductId(userId, productId);
        if(count > 0){
            return true;
        }else {
            return false;
        }
    }


}




