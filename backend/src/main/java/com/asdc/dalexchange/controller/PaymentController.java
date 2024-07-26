package com.asdc.dalexchange.controller;


import com.asdc.dalexchange.dto.PaymentDTO;
import com.asdc.dalexchange.model.ShippingAddress;
import com.asdc.dalexchange.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProductService productService;

    @Autowired
    private  SoldItemService soldItemService;

    @Autowired
    private ProductRatingService productRatingService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TradeRequestService tradeRequestService;

    @Autowired
    private ShippingAddressService shippingAddressService;

    @PostMapping("/save_order_details")
    public ResponseEntity<Long> saveOrderDetails(@RequestBody Map<String,Object> requestBody) {
        try {
            Long productId = Long.parseLong(requestBody.get("productId").toString());
            ShippingAddress getSavedShippingAddress = shippingAddressService.saveShippingAddress(requestBody);
            Long orderId = orderService.saveNewOrder(getSavedShippingAddress, productId);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1L);
        }
    }

    @PostMapping("/save_payment")
    public ResponseEntity<PaymentDTO> savePayment(@RequestBody Map<String,Object> requestBody) {
        PaymentDTO payment = paymentService.savePayment(requestBody);
        return ResponseEntity.ok(payment);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create_payment_intent")
    public ResponseEntity<Map<String, Object>> createPaymentIntent(@RequestBody Map<String,Object> requestBody) {
        try {
            String sessionId = paymentService.createPaymentIntent(requestBody);
            Map<String, Object> response = new HashMap<>();
            response.put("sessionId", sessionId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }



    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/payment_success")
    public  ResponseEntity<String> changeRequestStatus(@RequestBody Map<String,Object> requestBody){
        try {
            productService.markProductAsSold(requestBody);
            tradeRequestService.updateStatusByProduct(requestBody);
            soldItemService.saveSoldItem(requestBody);
          return ResponseEntity.status(HttpStatus.OK).body("success");
           }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @PostMapping("/save_rating")
    public  ResponseEntity<String> saveProductRating(@RequestBody Map<String,Object> requestBody) {
        productRatingService.saveRating(requestBody);
        return ResponseEntity.status(HttpStatus.OK).body("saved succefully");
    }


}
