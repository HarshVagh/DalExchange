package com.asdc.dalexchange.controller;


import com.asdc.dalexchange.model.PaymentRequest;
import com.asdc.dalexchange.model.ProductRating;
import com.asdc.dalexchange.model.ShippingAddress;
import com.asdc.dalexchange.model.TradeRequest;
import com.asdc.dalexchange.service.PaymentService;
import com.asdc.dalexchange.service.ShippingAddressService;
import com.asdc.dalexchange.service.TradeRequestService;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TradeRequestService tradeRequestService;

    @Autowired
    private ShippingAddressService shippingAddressService;

    @PostMapping("/confirm-payment")
    public ResponseEntity<String> confirmPayment(@RequestParam String sessionId) {
        try {
            Session session = Session.retrieve(sessionId);
            if ("complete".equals(session.getPaymentStatus())) {
                return ResponseEntity.ok("Payment successful");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment confirmation failed");
        }
    }

    @PostMapping("/shippingadress")
    public ResponseEntity<String> saveShippingAddress(@RequestBody ShippingAddress address) {
        try {
            shippingAddressService.saveShippingAddress(address);
            return ResponseEntity.ok("Shipping address added successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("shippingaddrees is not stored  failed");
        }
    }

    @GetMapping("/success")
    public ResponseEntity<String> paymentSuccess(@RequestParam String amount,
                                                 @RequestParam Long productId,
                                                 @RequestParam String paymentIntentId) {
        try {

            paymentService.savePayment(amount, productId, paymentIntentId);
            return ResponseEntity.ok("Payment successful and recorded.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment successful, but recording failed.");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String, Object>> createPaymentIntent(@RequestBody PaymentRequest request) {
        try {
            String sessionId = paymentService.createPaymentIntent(request.getProductId());
            Map<String, Object> response = new HashMap<>();
            response.put("sessionId", sessionId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /// change the payment status
    @PostMapping("/paymentsuccess")
    public  ResponseEntity<String> changeTradeRequestStatus(@RequestParam Long productId){
        try {
            String s = "succesfull";
          //  TradeRequest tradereuest = tradeRequestService.getApprovedTradeRequestAmount(productId);
          return ResponseEntity.status(HttpStatus.OK).body(s);
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @PostMapping("/save_rating")
    public  ResponseEntity<String> saveProductRating(@RequestBody Long productId,@RequestParam long rating ,  @RequestParam String review) {
        return null;
    }


}
