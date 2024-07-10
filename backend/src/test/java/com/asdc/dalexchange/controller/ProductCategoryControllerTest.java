package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.service.ProductCategoryService;
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
class ProductCategoryControllerTest {
    @InjectMocks
    private ProductCategoryController productCategoryController;

    @Mock
    private ProductCategoryService productCategoryService;

    @Test
    public void testGetProductCategories() {
        List<String> categories = List.of("Electronics", "Books", "Clothing");

        when(productCategoryService.findAll()).thenReturn(categories);

        List<String> result = productCategoryController.getProductCategories();

        assertEquals(categories, result);
        verify(productCategoryService).findAll();
    }
}