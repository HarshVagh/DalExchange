package com.asdc.dalexchange.service.imp;

import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.dto.ProductDetailsDTO;
import com.asdc.dalexchange.dto.SellerDTO;
import com.asdc.dalexchange.mapper.Mapper;
import com.asdc.dalexchange.mapper.impl.ProductMapperImpl;
import com.asdc.dalexchange.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.asdc.dalexchange.service.ProductDetailsService;
import com.asdc.dalexchange.service.ProductImageService;
import com.asdc.dalexchange.service.ProductService;
import com.asdc.dalexchange.service.ProductWishlistService;
import com.asdc.dalexchange.service.SellerService;


@Service
public class ProductDetailsServiceImp implements ProductDetailsService {

    private final ProductService productService;
    private final SellerService sellerService;
    private final ProductImageService productImageService;
    private final ProductWishlistService productWishlistService;
    private final Mapper<Product, ProductDetailsDTO> productDetailsMapper;
    private final ProductMapperImpl productMapper;

    @Autowired
    public ProductDetailsServiceImp(ProductService productService, SellerService sellerService, ProductImageService productImageService,
                                    ProductWishlistService productWishlistService, Mapper<Product, ProductDetailsDTO> productDetailsMapper,
                                    ProductMapperImpl productMapper) {
        this.productService = productService;
        this.sellerService = sellerService;
        this.productImageService = productImageService;
        this.productWishlistService = productWishlistService;
        this.productDetailsMapper = productDetailsMapper;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDetailsDTO getDetails(Long userId, Long productId) {
        // Get all the details of the product
        ProductDTO productDetails = productService.getProductById(productId);

        // Find the seller ID
        Long sellerId = productDetails.getSeller().getUserId();

        // Get the seller details
        Optional<SellerDTO> sellerDTO = sellerService.getSellerById(sellerId);

        // Get all image URLs of the given product
        List<String> productImageUrl = productImageService.getProductAllImages(productId);

        // Map ProductDTO to Product
        Product product = productMapper.mapFrom(productDetails);

        // Map Product to ProductDetailsDTO
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.mapTo(product);

        // Set the image URLs to ProductDetailsDTO
        productDetailsDTO.setImageurl(productImageUrl);

        // Set the seller joining date and rating

            productDetailsDTO.setSellerJoiningDate(sellerDTO.get().getSellerJoiningDate());
            productDetailsDTO.setRating(sellerDTO.get().getSellerRating());

        // Set the favorite status
        productDetailsDTO.setFavorite(productWishlistService.checkProductIsFavoriteByGivenUser(userId, productId));

        return productDetailsDTO;
    }
}




























/*@Service
public class ProductDetailsServiceImp implements ProductDetailsService {

    @Autowired
    private ProductService productService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductWishlistService productWishlistService;

    @Autowired
    private Mapper<Product, ProductDetailsDTO> productDetailsMapper; // Inject the mapper

    @Autowired
    private ProductMapperImpl productMapper ; // Inject the mapper

    @Override
    public ProductDetailsDTO DetailsOfProduct(Long userId, Long productId) {
        // get all the details of the product
        ProductDTO productDetails = productService.getProductById(productId);

        // find the seller id
        Long sellerId = productDetails.getSeller().getUserId();

        // seller
        Optional<SellerDTO> sellerDTO = sellerService.getSellerById(sellerId);

        //get the all image url of given product
        List<String> productImageUrl = productImageService.getProductAllImages(productId);

        // Map product to ProductDetailsDTO
         Product product = productMapper.mapFrom(productDetails);

        ProductDetailsDTO productDetailsDTO = productDetailsMapper.mapTo(product);

        // Set the all image URL to the ProductDetailsDTO
        productDetailsDTO.setImageurl(productImageUrl);

        productDetailsDTO.setSellerJoiningDate(sellerDTO.get().getSellerJoiningDate());

        //
        productDetailsDTO.setRating(sellerDTO.get().getSellerRating());

        // set the favorite
        productDetailsDTO.setFavorite(productWishlistService.checkProductIsFavoriteByGivenUser(userId, productId));

        return productDetailsDTO;
    }
}*/
























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
