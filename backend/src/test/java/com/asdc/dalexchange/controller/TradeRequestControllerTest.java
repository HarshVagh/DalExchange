package com.asdc.dalexchange.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.asdc.dalexchange.dto.TradeRequestDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Notification;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.TradeRequest;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.service.NotificationService;
import com.asdc.dalexchange.service.TradeRequestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TradeRequestControllerTest {

    @Mock
    private TradeRequestService tradeRequestService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private Mapper<TradeRequest, TradeRequestDTO> tradeRequestMapper;

    @InjectMocks
    private TradeRequestController tradeRequestController;

    @Test
    public void testGetSellRequests() {
        Long sellerId = 1L;
        TradeRequest tradeRequest = new TradeRequest();
        TradeRequestDTO tradeRequestDTO = new TradeRequestDTO();

        when(tradeRequestService.getSellerTradeRequests(sellerId)).thenReturn(List.of(tradeRequest));
        when(tradeRequestMapper.mapTo(tradeRequest)).thenReturn(tradeRequestDTO);

        List<TradeRequestDTO> result = tradeRequestController.getSellRequests();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(tradeRequestDTO, result.get(0));

        verify(tradeRequestService, times(1)).getSellerTradeRequests(sellerId);
        verify(tradeRequestMapper, times(1)).mapTo(tradeRequest);
    }

    @Test
    public void testGetBuyRequests() {
        Long buyerId = 1L;
        TradeRequest tradeRequest = new TradeRequest();
        TradeRequestDTO tradeRequestDTO = new TradeRequestDTO();

        when(tradeRequestService.getBuyerTradeRequests(buyerId)).thenReturn(List.of(tradeRequest));
        when(tradeRequestMapper.mapTo(tradeRequest)).thenReturn(tradeRequestDTO);

        List<TradeRequestDTO> result = tradeRequestController.getBuyRequests();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(tradeRequestDTO, result.get(0));

        verify(tradeRequestService, times(1)).getBuyerTradeRequests(buyerId);
        verify(tradeRequestMapper, times(1)).mapTo(tradeRequest);
    }

    @Test
    public void testUpdateTradeRequestStatus() {
        Long requestId = 1L;
        String status = "completed";
        TradeRequest updatedTradeRequest = new TradeRequest();
        TradeRequestDTO updatedTradeRequestDTO = new TradeRequestDTO();
        User buyer = new User();
        User seller = new User();
        Product product = new Product();
        product.setTitle("Test Product");

        updatedTradeRequest.setBuyer(buyer);
        updatedTradeRequest.setSeller(seller);
        updatedTradeRequest.setProduct(product);

        when(tradeRequestService.updateTradeRequestStatus(requestId, status)).thenReturn(updatedTradeRequest);
        when(tradeRequestMapper.mapTo(updatedTradeRequest)).thenReturn(updatedTradeRequestDTO);

        ResponseEntity<TradeRequestDTO> response = tradeRequestController.updateTradeRequestStatus(requestId, status);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTradeRequestDTO, response.getBody());

        verify(tradeRequestService, times(1)).updateTradeRequestStatus(requestId, status);
        verify(tradeRequestMapper, times(1)).mapTo(updatedTradeRequest);
        verify(notificationService, times(2)).sendNotification(any(Notification.class));
    }

    @Test
    public void testCreateTradeRequest() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", 1L);
        requestBody.put("sellerId", 1L);
        requestBody.put("requestedPrice", 100.0);

        TradeRequestDTO createdTradeRequestDTO = new TradeRequestDTO();

        when(tradeRequestService.createTradeRequest(requestBody)).thenReturn(createdTradeRequestDTO);

        ResponseEntity<TradeRequestDTO> response = tradeRequestController.createTradeRequest(requestBody);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdTradeRequestDTO, response.getBody());

        verify(tradeRequestService, times(1)).createTradeRequest(requestBody);
    }
}
