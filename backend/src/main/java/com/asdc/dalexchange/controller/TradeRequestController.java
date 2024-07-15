package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.TradeRequestDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.TradeRequest;
import com.asdc.dalexchange.service.TradeRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class TradeRequestController {
    @Autowired
    private TradeRequestService tradeRequestService;

    @Autowired
    private Mapper<TradeRequest, TradeRequestDTO> tradeRequestMapper;

    public TradeRequestController(TradeRequestService tradeRequestService, Mapper<TradeRequest, TradeRequestDTO> tradeRequestMapper) {
        this.tradeRequestService = tradeRequestService;
        this.tradeRequestMapper = tradeRequestMapper;
    }

    @GetMapping(path = "/sell_requests")
    public List<TradeRequestDTO> getSellRequests() {
        log.info("get sell_requests api endpoint called");
        long sellerId = 1L;
        List<TradeRequest> tradeRequests = tradeRequestService.getSellerTradeRequests(sellerId);
        return tradeRequests.stream()
                .map(tradeRequestMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/buy_requests")
    public List<TradeRequestDTO> getBuyRequests() {
        log.info("get buy_requests api endpoint called");
        long buyerId = 1L;
        List<TradeRequest> tradeRequests = tradeRequestService.getBuyerTradeRequests(buyerId);
        return tradeRequests.stream()
                .map(tradeRequestMapper::mapTo)
                .collect(Collectors.toList());
    }

    @PostMapping("/create_trade_request")
    public ResponseEntity<TradeRequestDTO> createTradeRequest(@RequestBody Map<String, Object> requestBody) {
        TradeRequestDTO createdTradeRequestDTO = tradeRequestService.createTradeRequest(requestBody);
        return new ResponseEntity<>(createdTradeRequestDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update_trade_status/{id}")
    public ResponseEntity<TradeRequestDTO> updateTradeRequestStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        log.info("updateTradeRequestStatus api endpoint called with id: {} and status: {}", id, status);
        TradeRequest updatedTradeRequest = tradeRequestService.updateTradeRequestStatus(id, status);
        TradeRequestDTO tradeRequestDTO = tradeRequestMapper.mapTo(updatedTradeRequest);
        return ResponseEntity.ok(tradeRequestDTO);
    }
}
