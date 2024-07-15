package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.TradeRequestDTO;
import com.asdc.dalexchange.model.TradeRequest;

import java.util.List;
import java.util.Map;

public interface TradeRequestService {
    List<TradeRequest> getBuyerTradeRequests(Long buyerId);
    List<TradeRequest> getSellerTradeRequests(Long sellerId);
    TradeRequest updateTradeRequestStatus(Long requestId, String status);
    public TradeRequestDTO createTradeRequest(Map<String, Object> requestBody);
}
