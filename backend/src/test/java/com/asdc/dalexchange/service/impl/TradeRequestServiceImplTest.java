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

import java.util.List;
import java.util.Optional;

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

    @Test
    public void testCreateTradeRequest() {
        TradeRequest tradeRequest = new TradeRequest();
        TradeRequest createdTradeRequest = new TradeRequest();

        when(tradeRequestRepository.save(tradeRequest)).thenReturn(createdTradeRequest);

        TradeRequest result = tradeRequestService.createTradeRequest(tradeRequest);

        assertNotNull(result);
        assertEquals(createdTradeRequest, result);

        verify(tradeRequestRepository, times(1)).save(tradeRequest);
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
}
