package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.TradeRequestDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Notification;
import com.asdc.dalexchange.model.TradeRequest;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.service.NotificationService;
import com.asdc.dalexchange.service.TradeRequestService;
import com.asdc.dalexchange.util.NotificationUtil;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class TradeRequestController {
    @Autowired
    private TradeRequestService tradeRequestService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private Mapper<TradeRequest, TradeRequestDTO> tradeRequestMapper;

    public TradeRequestController(
            TradeRequestService tradeRequestService,
            NotificationService notificationService,
            Mapper<TradeRequest, TradeRequestDTO> tradeRequestMapper) {
        this.tradeRequestService = tradeRequestService;
        this.notificationService = notificationService;
        this.tradeRequestMapper = tradeRequestMapper;
    }

    @GetMapping(path = "/sell_requests")
    public List<TradeRequestDTO> getSellRequests() {
        log.info("get sell_requests api endpoint called");
        List<TradeRequest> tradeRequests = tradeRequestService.getSellerTradeRequests();
        return tradeRequests.stream()
                .map(tradeRequestMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/buy_requests")
    public List<TradeRequestDTO> getBuyRequests() {
        log.info("get buy_requests api endpoint called");
        List<TradeRequest> tradeRequests = tradeRequestService.getBuyerTradeRequests();
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
        sendNotification(updatedTradeRequest, status);
        return ResponseEntity.ok(tradeRequestDTO);
    }

    private void sendNotification(TradeRequest tradeRequest, String status) {
        User buyer = tradeRequest.getBuyer();
        User seller = tradeRequest.getSeller();
        Notification notification = new Notification();
        String title = NotificationUtil.getTitle(status);
        String message = NotificationUtil.getMessage(status, tradeRequest.getProduct().getTitle());

        notification.setCreatedAt(LocalDateTime.now());
        notification.setIsRead(false);
        notification.setUser(buyer);
        notification.setTitle(title);
        notification.setMessage(message);
        notificationService.sendNotification(notification);

        if(status.equals("completed") || status.equals("canceled")) {
            String sellerTitle = NotificationUtil.getSellerTitle(status);
            String sellerMessage = NotificationUtil.getSellerMessage(status, tradeRequest.getProduct().getTitle());
            notification.setUser(seller);
            notification.setTitle(sellerTitle);
            notification.setMessage(sellerMessage);
            notificationService.sendNotification(notification);
        }
    }
}
