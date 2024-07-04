package com.asdc.dalexchange.service.imp;

import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.mapper.Mapper;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.service.ProductDetailsService;
import com.asdc.dalexchange.service.ProductImageService;
import com.asdc.dalexchange.service.ProductService;
import com.asdc.dalexchange.service.ProductWishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailsServiceImp implements ProductDetailsService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductWishlistService productWishlistService;

    @Autowired
    private Mapper<Product, ProductDetailsDTO> productDetailsMapper; // Inject the mapper

    @Override
    public ProductDetailsDTO DetailsOfProduct(Long userId, Long productId) {
        // get all the details of the product
        ProductDTO productDetails = productService.getProductById(productId);

        //get the all image url of given product
        List<String> productImageUrl = productImageService.getProductAllImages(productId);

        // Map product to ProductDetailsDTO
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.mapTo(productDetails);

        // Set the all image URL to the ProductDetailsDTO
        productDetailsDTO.setImageurl(productImageUrl);

        // set the favorite
        productDetailsDTO.setFavorite(productWishlistService.checkProductIsFavoriteByGivenUser(userId, productId));

        return productDetailsDTO;
    }
}
























/*
package com.asdc.dalexchange.service.imp;

import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.service.ProductDetailsService;
import com.asdc.dalexchange.service.ProductImageService;
import com.asdc.dalexchange.service.ProductService;
import com.asdc.dalexchange.service.ProductWishlistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailsServiceImp implements ProductDetailsService {

    @Autowired
    ProductService productService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    ProductWishlistService productWishlistService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ProductDetailsDTO DetailsOfProduct(Long userId, Long productId) {
        // get all the details of the product
        ProductDTO prodcutDetails = productService.getProductById(productId);

        //get the all image url of given product
        List<String> productImageUrl = productImageService.getProductAllImages(productId);

        // convert the model to DTO
        ProductDetailsDTO productDetailsDTO = modelMapper.map(prodcutDetails, ProductDetailsDTO.class);

        // Set The all Image URl To Given Product
        productDetailsDTO.setImageurl(productImageUrl);

        // set the seller joining date to DTO
        productDetailsDTO.setSellerJoiningDate(prodcutDetails.getSeller().getJoinedAt());

        // set the category name of the  product DTO
        productDetailsDTO.setCategory(prodcutDetails.getCategory().getName());

        // set the favorite
        productDetailsDTO.setFavorite(productWishlistService.checkProductIsFavoriteByGivenUser(userId, productId));

        return  productDetailsDTO;
    }

}
*/
