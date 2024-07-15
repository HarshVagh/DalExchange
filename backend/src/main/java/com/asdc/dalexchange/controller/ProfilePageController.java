package com.asdc.dalexchange.controller;
import com.asdc.dalexchange.dto.*;
import com.asdc.dalexchange.model.ProductWishlist;
import com.asdc.dalexchange.service.ProductRatingService;
import com.asdc.dalexchange.service.ProductWishlistService;
import com.asdc.dalexchange.service.ProfilePageService;
import com.asdc.dalexchange.service.SoldItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProfilePageController {

    @Autowired
    private ProfilePageService profilePageService;

    @Autowired
    private ProductRatingService productRatingService;

    @Autowired
    private SoldItemService soldItemService;

    @Autowired
    private ProductWishlistService productWishlistService;

    // home page of the user
    @GetMapping("/{userId}/profiledetails")
    public ResponseEntity<EditProfileDTO> profilledetails(@PathVariable Long userId) {
        EditProfileDTO updatedUser = profilePageService.editGetUserDetails(userId);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/saved_products/{userId}")
    public ResponseEntity<List<SavedProductDTO>> getAllSavedProducts(@PathVariable Long userId) {
        List<SavedProductDTO> savedProductDTOs = productWishlistService.GetAllsavedProduct(userId);
        return ResponseEntity.ok(savedProductDTOs);
    }

    @PutMapping("/saved_products/{userId}/remove_product/{productId}")
    public ResponseEntity<List<SavedProductDTO>> removesavedproduct(@PathVariable Long userId,@PathVariable Long productId) {
        List<SavedProductDTO> savedProductDTOs = productWishlistService.GetAllsavedProduct(userId);
        return ResponseEntity.ok(savedProductDTOs);
    }


    @GetMapping("/sold_products/{userId}")
    public ResponseEntity<List<SoldItemDTO>> getAllSoldProducts(@PathVariable Long userId) {
        List<SoldItemDTO> soldProductDTOs = soldItemService.GetallSoldProduct(userId);
        return ResponseEntity.ok(soldProductDTOs);
    }

    @GetMapping("/purchased_products/{userId}")
    public ResponseEntity<List<PurchaseProductDTO>> getAllPurchasedProducts(@PathVariable Long userId) {
        List<PurchaseProductDTO> purchasedProductDTOs = productWishlistService.GetallPurchasedProduct(userId);
        return ResponseEntity.ok(purchasedProductDTOs);
    }

    @GetMapping("/product_ratings/{userId}")
    public ResponseEntity<List<ProductRatingDTO>> getAllProductRatings(@PathVariable Long userId) {
        List<ProductRatingDTO> productRatingDTOs = productRatingService.GetAllProductRating(userId);
        return ResponseEntity.ok(productRatingDTOs);
    }

    @PutMapping("/edit_user/{userId}")
    public ResponseEntity<String> editUserDetails(@RequestBody EditProfileDTO editProfileDTO, @PathVariable Long userId) {
        EditProfileDTO updatedUser = profilePageService.editUserDetails(userId,editProfileDTO);
        return ResponseEntity.ok().body("Profile Edited Sucessfully");
    }

    @PostMapping("/{userid}/{productid}/removesaved")
    public ResponseEntity<String> unmarkAsFavorite(@PathVariable long userid, @PathVariable long productid) {
        ProductWishlistDTO productWishlistDTO = new ProductWishlistDTO();
        productWishlistDTO.setUserId(userid);
        productWishlistDTO.setProductId(productid);
        // Assuming ProductWishlistService is injected properly
        //ProductWishlist productWishlistDTO1 = new ProductWishlist();
        boolean whishlist = productWishlistService.markProductAsFavorite(userid, productid);

        return ResponseEntity.ok().body("Product added Sucessfully in wishlist.");
    }

}
