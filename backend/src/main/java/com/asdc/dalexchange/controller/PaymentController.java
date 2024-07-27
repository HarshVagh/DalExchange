package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.PaymentDTO;
import com.asdc.dalexchange.model.ShippingAddress;
import com.asdc.dalexchange.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;
    private final ProductService productService;
    private final SoldItemService soldItemService;
    private final ProductRatingService productRatingService;
    private final OrderService orderService;
    private final TradeRequestService tradeRequestService;
    private final ShippingAddressService shippingAddressService;

    @PostMapping("/save_order_details")
    public ResponseEntity<Long> saveOrderDetails(@RequestBody Map<String, Object> requestBody) {
        try {
            Long productId = Long.parseLong(requestBody.get("productId").toString());
            log.info("Saving order details for productId: {}", productId);

            ShippingAddress savedShippingAddress = shippingAddressService.saveShippingAddress(requestBody);
            Long orderId = orderService.saveNewOrder(savedShippingAddress, productId);

            log.info("Order created with orderId: {}", orderId);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
        } catch (Exception e) {
            log.error("Error saving order details: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1L);
        }
    }

    @PostMapping("/save_payment")
    public ResponseEntity<PaymentDTO> savePayment(@RequestBody Map<String, Object> requestBody) {
        log.info("Saving payment details");

        PaymentDTO payment = paymentService.savePayment(requestBody);
        log.info("Payment saved with ID: {}", payment.getPaymentId());

        return ResponseEntity.ok(payment);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create_payment_intent")
    public ResponseEntity<Map<String, Object>> createPaymentIntent(@RequestBody Map<String, Object> requestBody) {
        try {
            log.info("Creating payment intent");

            String sessionId = paymentService.createPaymentIntent(requestBody);
            Map<String, Object> response = new HashMap<>();
            response.put("sessionId", sessionId);

            log.info("Payment intent created with sessionId: {}", sessionId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("Error creating payment intent: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/payment_success")
    public ResponseEntity<String> changeRequestStatus(@RequestBody Map<String, Object> requestBody) {
        try {
            log.info("Processing payment success for requestBody: {}", requestBody);

            productService.markProductAsSold(requestBody);
            tradeRequestService.updateStatusByProduct(requestBody);
            soldItemService.saveSoldItem(requestBody);

            log.info("Payment success processed successfully");
            return ResponseEntity.status(HttpStatus.OK).body("success");
        } catch (Exception e) {
            log.error("Error processing payment success: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/save_rating")
    public ResponseEntity<String> saveProductRating(@RequestBody Map<String, Object> requestBody) {
        log.info("Saving product rating");

        productRatingService.saveRating(requestBody);
        log.info("Product rating saved successfully");

        return ResponseEntity.status(HttpStatus.OK).body("saved successfully");
    }
}
