package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.TradeRequestDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.TradeRequest;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.repository.TradeRequestRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.TradeRequestService;
import com.asdc.dalexchange.specifications.TradeRequestSpecification;
import com.asdc.dalexchange.util.AuthUtil;
import com.asdc.dalexchange.util.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@Slf4j
public class TradeRequestServiceImpl implements TradeRequestService {
    private TradeRequestRepository tradeRequestRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private Mapper<TradeRequest, TradeRequestDTO> tradeRequestMapper;

    public TradeRequestServiceImpl(
            TradeRequestRepository tradeRequestRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            Mapper<TradeRequest, TradeRequestDTO> tradeRequestMapper) {
        this.tradeRequestRepository = tradeRequestRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.tradeRequestMapper = tradeRequestMapper;
    }

    @Override
    public List<TradeRequest> getBuyerTradeRequests() {
        log.info("getBuyerTradeRequests call started in the TradeRequestServiceImpl");
        Long buyerId = 2L; //AuthUtil.getCurrentUserId(userRepository);
        Specification<TradeRequest> spec = TradeRequestSpecification.hasBuyerId(buyerId);
        return tradeRequestRepository.findAll(spec);
    }

    @Override
    public List<TradeRequest> getSellerTradeRequests() {
        log.info("getSellerTradeRequests call started in the TradeRequestServiceImpl");
        Long sellerId = 2L; //AuthUtil.getCurrentUserId(userRepository);
        Specification<TradeRequest> spec = TradeRequestSpecification.hasSellerId(sellerId);
        return tradeRequestRepository.findAll(spec);
    }

    @Override
    public TradeRequest updateTradeRequestStatus(Long requestId, String status) {
        log.info("updateTradeRequestStatus call started in the TradeRequestServiceImpl with requestId: {} and status: {}", requestId, status);
        TradeRequest tradeRequest = tradeRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("TradeRequest id: "+requestId));

        tradeRequest.setRequestStatus(status);
        return tradeRequestRepository.save(tradeRequest);
    }

    @Override
    public TradeRequestDTO createTradeRequest(Map<String, Object> requestBody) {
        log.info("createTradeRequest call started in the TradeRequestServiceImpl with data: {}", requestBody);

        Long productId = Long.valueOf(requestBody.get("productId").toString());
        Long sellerId = Long.valueOf(requestBody.get("sellerId").toString());
        double requestedPrice = Double.parseDouble(requestBody.get("requestedPrice").toString());

        TradeRequest tradeRequest = new TradeRequest();
        Product product = findProductById(productId);
        User seller = findUserById(sellerId);
        User buyer = findUserById(2L); // TODO: replace with current logged in user id

        tradeRequest.setProduct(product);
        tradeRequest.setSeller(seller);
        tradeRequest.setBuyer(buyer);
        tradeRequest.setRequestedPrice(requestedPrice);
        tradeRequest.setRequestStatus("pending");
        tradeRequest.setRequestedAt(LocalDateTime.now());

        TradeRequest createdTradeRequest = tradeRequestRepository.save(tradeRequest);
        return tradeRequestMapper.mapTo(createdTradeRequest);
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Override
    public double getApprovedTradeRequestAmount(Long buyerId, Long productId) {
        List<TradeRequest> tradeRequest = tradeRequestRepository.findAll(
                where(TradeRequestSpecification.hasBuyerId(buyerId))
                        .and(TradeRequestSpecification.hasProductId(productId))
                        .and(TradeRequestSpecification.hasRequestStatus("approved")));
        TradeRequest firsttraderequest = tradeRequest.get(0);
        return firsttraderequest.getRequestedPrice();
    }
}
