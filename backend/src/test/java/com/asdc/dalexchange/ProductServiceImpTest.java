package com.asdc.dalexchange;
import com.asdc.dalexchange.dto.ProductDTO;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.service.imp.ProductServiceImp;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductServiceImpTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductServiceImp productServiceImp;

    @Test
    public void testGetProductById_Success() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setProductId(productId);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        // Act
        ProductDTO result = productServiceImp.getProductById(productId);

        // Assert
        assertEquals(productDTO, result);
    }

    @Test
    public void testGetProductById_NotFound() {
        // Arrange
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productServiceImp.getProductById(productId);
        });

        assertEquals("Product not found with ID: " + productId, exception.getMessage());
    }
}

