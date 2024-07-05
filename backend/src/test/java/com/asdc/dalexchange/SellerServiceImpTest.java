package com.asdc.dalexchange;

import com.asdc.dalexchange.dto.SellerDTO;
import com.asdc.dalexchange.mapper.impl.SellerDetailsMapperImpl;
import com.asdc.dalexchange.model.Seller;
import com.asdc.dalexchange.repository.SellerRepository;

import com.asdc.dalexchange.service.imp.SellerServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class SellerServiceImpTest {

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private SellerDetailsMapperImpl sellerDetailsMapper;

    @InjectMocks
    private SellerServiceImp sellerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getSellerById_Exists() {
        // Mock data
        Long sellerId = 1L;
        Seller seller = new Seller();
        seller.setSellerId(sellerId);
        SellerDTO expectedSellerDTO = new SellerDTO();
        expectedSellerDTO.setSellerId(sellerId);

        // Mock repository behavior
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));
        when(sellerDetailsMapper.mapTo(seller)).thenReturn(expectedSellerDTO);

        // Call method under test
        Optional<SellerDTO> result = sellerService.getSellerById(sellerId);

        // Assertions
        assertEquals(expectedSellerDTO, result.orElse(null));
        verify(sellerRepository, times(1)).findById(sellerId);
        verify(sellerDetailsMapper, times(1)).mapTo(seller);
    }

    @Test
    public void getSellerById_NotFound() {
        // Mock data
        Long sellerId = 2L;

        // Mock repository behavior
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.empty());

        // Call method under test
        Optional<SellerDTO> result = sellerService.getSellerById(sellerId);

        // Assertions
        assertFalse(result.isPresent());
        verify(sellerRepository, times(1)).findById(sellerId);
        verifyNoInteractions(sellerDetailsMapper); // Ensure mapper is not called
    }
}

