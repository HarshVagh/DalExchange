package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.PaginatedResponse;
import com.asdc.dalexchange.dto.ProductListingDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.service.ProductListingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductListingControllerTest {
    @InjectMocks
    private ProductListingController productListingController;

    @Mock
    private ProductListingService productListingService;

    @Mock
    private Mapper<Product, ProductListingDTO> productListingMapper;

    @Test
    public void testGetProductListing() {
        Product product = new Product();
        ProductListingDTO productListingDTO = new ProductListingDTO();
        List<Product> products = List.of(product);
        Page<Product> productPage = new PageImpl<>(products);
        List<ProductListingDTO> productListingDTOs = List.of(productListingDTO);

        when(productListingService.findByCriteria(any(), any(), any(), any(), any(), any())).thenReturn(productPage);
        when(productListingMapper.mapTo(product)).thenReturn(productListingDTO);

        PaginatedResponse<ProductListingDTO> result = productListingController.getProductListing(0, 10, null, null, null, null, null);

        assertEquals(productListingDTOs, result.getContent());
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getTotalElements());
        verify(productListingService).findByCriteria(any(), any(), any(), any(), any(), any());
        verify(productListingMapper).mapTo(product);
    }
}