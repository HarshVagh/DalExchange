package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.ProductDTO;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {


    //get all the product by id
    ProductDTO getProductById(Long productId);

   // UserDTO getUserById(int userId);


}
