package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.model.TradeRequest;
import com.asdc.dalexchange.repository.TradeRequestRepository;
import com.asdc.dalexchange.service.TradeRequestService;
import com.asdc.dalexchange.specifications.TradeRequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TradeRequestServiceImpl implements TradeRequestService {

    @Autowired
    TradeRequestRepository tradeRequestRepository;

    public TradeRequestServiceImpl(TradeRequestRepository tradeRequestRepository) {
        this.tradeRequestRepository = tradeRequestRepository;
    }

    @Override
    public List<TradeRequest> getBuyerTradeRequests(Long buyerId) {
        log.info("getBuyerTradeRequests call started in the TradeRequestServiceImpl");
        Specification<TradeRequest> spec = TradeRequestSpecification.hasBuyerId(buyerId);
        return tradeRequestRepository.findAll(spec);
    }

    @Override
    public List<TradeRequest> getSellerTradeRequests(Long sellerId) {
        log.info("getSellerTradeRequests call started in the TradeRequestServiceImpl");
        Specification<TradeRequest> spec = TradeRequestSpecification.hasSellerId(sellerId);
        return tradeRequestRepository.findAll(spec);
    }
}
