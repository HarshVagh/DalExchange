package com.asdc.dalexchange.controller;
import com.asdc.dalexchange.dto.*;
import com.asdc.dalexchange.service.ProductRatingService;
import com.asdc.dalexchange.service.ProductWishlistService;
import com.asdc.dalexchange.service.ProfilePageService;
import com.asdc.dalexchange.service.SoldItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfilePageController {

    @Autowired
    private ProfilePageService profilePageService;

    @Autowired
    private ProductRatingService productRatingService;

    @Autowired
    private SoldItemService soldItemService;

    @Autowired
    private ProductWishlistService productWishlistService;

    @GetMapping("")
    public ResponseEntity<EditProfileDTO> profileDetails() {
        EditProfileDTO updatedUser = profilePageService.editGetUserDetails();
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/saved_products")
    public ResponseEntity<List<SavedProductDTO>> getAllSavedProducts() {
        List<SavedProductDTO> savedProductDTOs = productWishlistService.getAllSavedProducts();
        return ResponseEntity.ok(savedProductDTOs);
    }

    @GetMapping("/sold_products")
    public ResponseEntity<List<SoldItemDTO>> getAllSoldProducts() {
        List<SoldItemDTO> soldProductDTOs = soldItemService.GetallSoldProduct();
        return ResponseEntity.ok(soldProductDTOs);
    }

    @GetMapping("/purchased_products")
    public ResponseEntity<List<PurchaseProductDTO>> getAllPurchasedProducts() {
        List<PurchaseProductDTO> purchasedProductDTOs = productWishlistService.getAllPurchasedProduct();
        return ResponseEntity.ok(purchasedProductDTOs);
    }

    @GetMapping("/product_ratings")
    public ResponseEntity<List<ProductRatingDTO>> getAllProductRatings() {
        List<ProductRatingDTO> productRatingDTOs = productRatingService.allReviewOfAllSoldItemsOfUser();
        return ResponseEntity.ok(productRatingDTOs);
    }

    @PutMapping("/edit_user")
    public ResponseEntity<EditProfileDTO> editUserDetails(@RequestBody EditProfileDTO editProfileDTO) {
        EditProfileDTO updatedUser = profilePageService.editUserDetails(editProfileDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/remove_saved/{productId}")
    public ResponseEntity<Object> unmarkAsFavorite(@PathVariable long productId) {
        boolean isUpdated = productWishlistService.markProductAsFavorite(productId);
       // if (isUpdated) {
            return ResponseEntity.ok("Product removed successfully");
       // }
       // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
