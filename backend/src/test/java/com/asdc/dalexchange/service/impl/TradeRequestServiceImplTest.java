package com.asdc.dalexchange.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.asdc.dalexchange.model.TradeRequest;
import com.asdc.dalexchange.repository.TradeRequestRepository;
import com.asdc.dalexchange.specifications.TradeRequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TradeRequestServiceImplTest {

    @Mock
    private TradeRequestRepository tradeRequestRepository;

    @InjectMocks
    private TradeRequestServiceImpl tradeRequestService;

    @Test
    public void testGetBuyerTradeRequests() {
        Long buyerId = 1L;
        TradeRequest tradeRequest = new TradeRequest();
        Specification<TradeRequest> spec = TradeRequestSpecification.hasBuyerId(buyerId);

        when(tradeRequestRepository.findAll(any(Specification.class))).thenReturn(List.of(tradeRequest));

        List<TradeRequest> result = tradeRequestService.getBuyerTradeRequests(buyerId);

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

        List<TradeRequest> result = tradeRequestService.getSellerTradeRequests(sellerId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(tradeRequest, result.get(0));

        verify(tradeRequestRepository, times(1)).findAll(any(Specification.class));
    }
}
