package com.asdc.dalexchange.controller;


import com.asdc.dalexchange.service.PaymentService;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

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

    //@CrossOrigin
    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map> createPaymentIntent(@RequestParam("amount") String amount, @RequestParam("productId") long productId, @RequestParam("userId") long userId ,Principal principal) {
        try {
            String sesisonId = paymentService.createPaymentIntent(amount,productId,userId,principal);
            Map<String, Object> response = new HashMap<>();
            response.put("sessionId", sesisonId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
