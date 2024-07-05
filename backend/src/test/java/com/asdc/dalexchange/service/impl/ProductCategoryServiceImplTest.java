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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}
