package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.AddProductDTO;
import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.dto.ProductModerationDTO;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ProductCategory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {

    ProductDTO getProductById(Long productId);
    List<ProductModerationDTO> getAllProducts();
    ProductModerationDTO updateProduct(Long productId, ProductModerationDTO updatedProductDetails);
    void unlistProduct(Long productId);
    Product addProduct(AddProductDTO addProductDTO, ProductCategory category, MultipartFile file) throws IOException;
    ProductCategory getCategoryById(Long categoryId);
    Product getProductByID(Long id);

}
