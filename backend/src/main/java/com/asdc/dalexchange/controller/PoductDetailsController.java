package com.asdc.dalexchange.controller;

import ch.qos.logback.core.model.Model;
import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.dto.ProductWishlistDTO;
import com.asdc.dalexchange.dto.SellerDTO;
import com.asdc.dalexchange.model.ProductWishlist;
import com.asdc.dalexchange.service.*;
import com.asdc.dalexchange.service.imp.ProductDetailsServiceImp;
import com.asdc.dalexchange.service.imp.ProductWishListServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/dalexchange")
public class PoductDetailsController {
    @Autowired
    private ProductDetailsService productDetailsService;

    @Autowired
    private ProductWishlistService productWishlistService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{userid}/{productid}")
    public ResponseEntity<ProductDetailsDTO> product(@PathVariable long userid ,@PathVariable long productid) {
        ProductDetailsDTO productDetailsDTO = productDetailsService.DetailsOfProduct(userid,productid);
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




/*    @PostMapping("/{userid}/{productid}/favorite")
    public ResponseEntity<String> markAsFavorite(@PathVariable long userid, @PathVariable long productid) {

        ProductWishlistDTO productWishlistDTO = new ProductWishlistDTO();
        productWishlistDTO.setUserId(userid);
        productWishlistDTO.setProductId(productid);

        // Assuming ProductWishlistService is injected properly
        ProductWishlist productWishlistDTO1 = new ProductWishlist();
        productWishlistDTO1 = this.productWishlistService.markProductAsFavorite(userid, productid);

        return ResponseEntity.ok().body("Product added Sucessfully in wishlist.");
    }*/


    ////////////////////////////////
    // check the image url
  /*  @Autowired
    private ProductImageService productImageService;

    @GetMapping("/{productid}")
    public List<String> product(@PathVariable long productid) {
        List<String> urllist = productImageService.getProductAllImages(productid);
        System.out.println("urllist: " + urllist);
        // int sellerId = productDTO.getSellerId();
        return urllist;
    }*/

//////////////////////////////////////////
    // check the product service
   /* @Autowired
    private ProductService productService;

    @GetMapping("/{productid}")
    public ResponseEntity<ProductDTO> product(@PathVariable long productid) {
        ProductDTO productDetailsDTO = productService.getProductById(productid);
        // int sellerId = productDTO.getSellerId();
        return ResponseEntity.ok().body(productDetailsDTO);
    }*/

//////////////////////////////////////////////////////
 /*   //check the seller service
    // check the product service
    @Autowired
    private SellerService sellerService;

    @GetMapping("/{productid}")
    public ResponseEntity<SellerDTO> product(@PathVariable long productid) {
        SellerDTO productDetailsDTO = sellerService.getSellerInfo(productid);
        // int sellerId = productDTO.getSellerId();
        return ResponseEntity.ok().body(productDetailsDTO);
    }*/
}
