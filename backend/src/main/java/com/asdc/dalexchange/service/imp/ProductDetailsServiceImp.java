package com.asdc.dalexchange.service.imp;

import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.dto.SellerDTO;
import com.asdc.dalexchange.service.ProductDetailsService;
import com.asdc.dalexchange.service.ProductImageService;
import com.asdc.dalexchange.service.ProductService;
import com.asdc.dalexchange.service.SellerService;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailsServiceImp implements ProductDetailsService {

    @Autowired
    ProductService productService;

    @Autowired
    SellerService sellerService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ProductDetailsDTO DetailsOfProduct(Long productId) {
        // get all the details of the product
        ProductDTO prodcutDetails = productService.getProductById(productId);

        //get the seller id from product details
        Long SellerId = prodcutDetails.getSeller().getUserId();

        //get the all image url of given product
         List<String> productImageUrl = productImageService.getProductAllImages(productId);

         //get all the seller services
        SellerDTO sellerDetails = sellerService.getSellerInfo(SellerId);

        // convert the model to DTO
        ProductDetailsDTO productDetailsDTO = modelMapper.map(prodcutDetails, ProductDetailsDTO.class);

        // Set The all Image URl To Given Product
        productDetailsDTO.setImageurl(productImageUrl);

        // set the seller joining date to DTO
        productDetailsDTO.setSellerJoiningDate(sellerDetails.getSellerJoiningDate());

        // set the category name of the  product DTO
        productDetailsDTO.setCategory(prodcutDetails.getCategory().getName());


        return  productDetailsDTO;
    }

    @Override
    public ProductDetailsDTO markProductAsFavorite(Long userid, Long productid) {
        return null;
    }

    @Override
    public ProductDetailsDTO sendBuyRequest(Long userid, Long productid) {
        return null;
    }
}
