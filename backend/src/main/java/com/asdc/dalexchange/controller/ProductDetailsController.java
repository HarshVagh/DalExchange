package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.dto.ProductWishlistDTO;
import com.asdc.dalexchange.model.ProductWishlist;
import com.asdc.dalexchange.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/dalexchange")
public class ProductDetailsController {
    @Autowired
    private ProductDetailsService productDetailsService;

    @Autowired
    private ProductWishlistService productWishlistService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{userid}/{productid}")
    public ResponseEntity<ProductDetailsDTO> product(@PathVariable long userid ,@PathVariable long productid) {
        ProductDetailsDTO productDetailsDTO = productDetailsService.getDetails(userid,productid);
        // int sellerId = productDTO.getSellerId();
        return ResponseEntity.ok().body(productDetailsDTO);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/{userid}/{productid}/favorite")
    public ResponseEntity<String> markAsFavorite(@PathVariable long userid, @PathVariable long productid) {

        ProductWishlistDTO productWishlistDTO = new ProductWishlistDTO();
        productWishlistDTO.setUserId(userid);
        productWishlistDTO.setProductId(productid);

        // Assuming ProductWishlistService is injected properly
        ProductWishlist productWishlistDTO1 = new ProductWishlist();
        productWishlistDTO1 = productWishlistService.markProductAsFavorite(userid, productid);

        return ResponseEntity.ok().body("Product added Sucessfully in wishlist.");
    }
}
