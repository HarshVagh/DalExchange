package com.asdc.dalexchange.service;

import com.asdc.dalexchange.model.TradeRequest;

import java.util.List;

public interface TradeRequestService {
    List<TradeRequest> getBuyerTradeRequests(Long buyerId);
    List<TradeRequest> getSellerTradeRequests(Long sellerId);
    TradeRequest updateTradeRequestStatus(Long requestId, String status);
    TradeRequest createTradeRequest(TradeRequest tradeRequest);
}
