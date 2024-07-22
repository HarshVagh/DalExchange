package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.TradeRequestDTO;
import com.asdc.dalexchange.model.TradeRequest;

import java.util.List;
import java.util.Map;

public interface TradeRequestService {
    List<TradeRequest> getBuyerTradeRequests();
    List<TradeRequest> getSellerTradeRequests();
    TradeRequest updateTradeRequestStatus(Long requestId, String status);
    TradeRequestDTO createTradeRequest(Map<String, Object> requestBody);
    public double getApprovedTradeRequestAmount(Long productId);
}
