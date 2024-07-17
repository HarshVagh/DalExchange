package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.dto.ProductModerationDTO;
import com.asdc.dalexchange.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    //get all the product by id
    ProductDTO getProductById(Long productId);
    List<ProductModerationDTO> getAllProducts();
    ProductModerationDTO updateProduct(Long productId, ProductModerationDTO updatedProductDetails);
    void unlistProduct(Long productId);


}
