package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.TradeRequestDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.TradeRequest;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.repository.TradeRequestRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.specifications.TradeRequestSpecification;
import com.asdc.dalexchange.util.AuthUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TradeRequestServiceImplTest {

    @Mock
    private TradeRequestRepository tradeRequestRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper<TradeRequest, TradeRequestDTO> tradeRequestMapper;

    @InjectMocks
    private TradeRequestServiceImpl tradeRequestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetBuyerTradeRequests() {
        Long buyerId = 1L;
        TradeRequest tradeRequest = new TradeRequest();
        Specification<TradeRequest> spec = TradeRequestSpecification.hasBuyerId(buyerId);

        when(tradeRequestRepository.findAll(any(Specification.class))).thenReturn(List.of(tradeRequest));

        List<TradeRequest> result = tradeRequestService.getBuyerTradeRequests();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(tradeRequest, result.get(0));

        verify(tradeRequestRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    public void testGetSellerTradeRequests() {
        Long sellerId = 1L;
        TradeRequest tradeRequest = new TradeRequest();
        Specification<TradeRequest> spec = TradeRequestSpecification.hasSellerId(sellerId);

        when(tradeRequestRepository.findAll(any(Specification.class))).thenReturn(List.of(tradeRequest));

        List<TradeRequest> result = tradeRequestService.getSellerTradeRequests();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(tradeRequest, result.get(0));

        verify(tradeRequestRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    public void testUpdateTradeRequestStatus() {
        Long requestId = 1L;
        String status = "approved";
        TradeRequest tradeRequest = new TradeRequest();
        tradeRequest.setRequestStatus("pending");
        TradeRequest updatedTradeRequest = new TradeRequest();
        updatedTradeRequest.setRequestStatus(status);

        when(tradeRequestRepository.findById(requestId)).thenReturn(Optional.of(tradeRequest));
        when(tradeRequestRepository.save(tradeRequest)).thenReturn(updatedTradeRequest);

        TradeRequest result = tradeRequestService.updateTradeRequestStatus(requestId, status);

        assertNotNull(result);
        assertEquals(status, result.getRequestStatus());

        verify(tradeRequestRepository, times(1)).findById(requestId);
        verify(tradeRequestRepository, times(1)).save(tradeRequest);
    }

    @Test
    public void testCreateTradeRequest() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", 1L);
        requestBody.put("sellerId", 1L);
        requestBody.put("requestedPrice", 100.0);

        Product product = new Product();
        User seller = new User();
        User buyer = new User();
        TradeRequest createdTradeRequest = new TradeRequest();
        TradeRequestDTO createdTradeRequestDTO = new TradeRequestDTO();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(seller));

        try (MockedStatic<AuthUtil> mockedAuthUtil = Mockito.mockStatic(AuthUtil.class)) {
            mockedAuthUtil.when(() -> AuthUtil.getCurrentUser(userRepository)).thenReturn(buyer);
            when(productRepository.findById(1L)).thenReturn(Optional.of(product));
            when(userRepository.findById(1L)).thenReturn(Optional.of(seller));
            when(userRepository.findById(0L)).thenReturn(Optional.of(buyer));
            when(tradeRequestRepository.save(any(TradeRequest.class))).thenReturn(createdTradeRequest);
            when(tradeRequestMapper.mapTo(createdTradeRequest)).thenReturn(createdTradeRequestDTO);

            TradeRequestDTO result = tradeRequestService.createTradeRequest(requestBody);

            assertNotNull(result);
            assertEquals(createdTradeRequestDTO, result);

            verify(productRepository, times(1)).findById(1L);
            verify(userRepository, times(1)).findById(1L);
            verify(userRepository, times(1)).findById(0L);
            verify(tradeRequestRepository, times(1)).save(any(TradeRequest.class));
            verify(tradeRequestMapper, times(1)).mapTo(createdTradeRequest);
        }
    }
}
