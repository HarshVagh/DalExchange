package com.asdc.dalexchange.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.asdc.dalexchange.dto.TradeRequestDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.TradeRequest;
import com.asdc.dalexchange.service.TradeRequestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TradeRequestControllerTest {

    @Mock
    private TradeRequestService tradeRequestService;

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

        List<TradeRequestDTO> result = tradeRequestController.getSellRequests(sellerId);

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

        List<TradeRequestDTO> result = tradeRequestController.getBuyRequests(buyerId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(tradeRequestDTO, result.get(0));

        verify(tradeRequestService, times(1)).getBuyerTradeRequests(buyerId);
        verify(tradeRequestMapper, times(1)).mapTo(tradeRequest);
    }
}
