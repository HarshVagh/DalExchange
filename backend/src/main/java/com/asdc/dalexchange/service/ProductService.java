package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    ProductDTO getProductById(Long productId);
    List<Product> getAllProducts();
    Product updateProduct(Long productId, Product updatedProductDetails);
    void unlistProduct(Long productId);

}
