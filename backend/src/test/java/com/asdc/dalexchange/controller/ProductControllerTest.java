package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.ProductListingDTO;
import com.asdc.dalexchange.mappers.impl.ProductListingMapperImpl;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.service.ProductListingService;
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
class ProductControllerTest {
    @InjectMocks
    private ProductListingController productListingController;

    @Mock
    private ProductListingService productListingService;

    @Mock
    private ProductListingMapperImpl productListingMapper;

    @Test
    public void testGetProductListing() {
        Product product = new Product();
        ProductListingDTO productListingDTO = new ProductListingDTO();
        List<Product> products = List.of(product);
        List<ProductListingDTO> productListingDTOs = List.of(productListingDTO);

        when(productListingService.findAll()).thenReturn(products);
        when(productListingMapper.mapTo(product)).thenReturn(productListingDTO);

        List<ProductListingDTO> result = productListingController.getProductListing();

        assertEquals(productListingDTOs, result);
        verify(productListingService).findAll();
        verify(productListingMapper).mapTo(product);
    }
}