package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductListingServiceImplTest {
    @InjectMocks
    private ProductListingServiceImpl productListingService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void testFindAll() {
        Product product = new Product();
        Iterable<Product> products = List.of(product);
        when(productRepository.findAll()).thenReturn(products);
        List<Product> result = productListingService.findAll();
        assertEquals(products, result);
        verify(productRepository).findAll();
    }
}