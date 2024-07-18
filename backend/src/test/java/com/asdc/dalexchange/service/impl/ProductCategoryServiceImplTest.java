package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.model.ProductCategory;
import com.asdc.dalexchange.repository.ProductCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductCategoryServiceImplTest {
    @InjectMocks
    private ProductCategoryServiceImpl productCategoryService;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void testFindAll() {
        ProductCategory category1 = new ProductCategory();
        category1.setName("Electronics");

        ProductCategory category2 = new ProductCategory();
        category2.setName("Books");

        List<ProductCategory> categories = List.of(category1, category2);
        List<String> expectedNames = List.of("Electronics", "Books");

        when(productCategoryRepository.findAll()).thenReturn(categories);

        List<String> result = productCategoryService.findAll();

        assertEquals(expectedNames, result);
        verify(productCategoryRepository).findAll();
    }

    @Test
    void testFindAllCategories() {
        ProductCategory category1 = new ProductCategory();
        category1.setCategoryId(1L);
        category1.setName("Category 1");

        ProductCategory category2 = new ProductCategory();
        category2.setCategoryId(2L);
        category2.setName("Category 2");

        List<ProductCategory> categories = Arrays.asList(category1, category2);

        when(productCategoryRepository.findAll()).thenReturn(categories);

        List<ProductCategory> result = productCategoryService.findAllCategories();

        assertEquals(2, result.size());
        assertEquals("Category 1", result.get(0).getName());
        assertEquals("Category 2", result.get(1).getName());
        verify(productCategoryRepository, times(1)).findAll();
    }
}
