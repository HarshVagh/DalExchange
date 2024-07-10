package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.model.OrderDetails;
import com.asdc.dalexchange.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderServiceImpl orderServiceImpl;

    @Autowired
    public AdminOrderController(OrderServiceImpl orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }

    @GetMapping("/orderDetails/{orderId}")
    public OrderDetails getOrderById(@PathVariable int orderId) {
        return orderServiceImpl.getOrderById(orderId);
    }

    @PutMapping("/update/{orderId}")
    public OrderDetails updateOrder(
            @PathVariable int orderId,
            @RequestBody OrderDetails updatedOrderDetails) {
        return orderServiceImpl.updateOrder(orderId, updatedOrderDetails);
    }

    @PutMapping("/cancel/{orderId}")
    public void cancelOrder(
            @PathVariable int orderId,
            @RequestBody String adminComments) {
        orderServiceImpl.cancelOrder(orderId, adminComments);
    }

    @PutMapping("/refund/{orderId}")
    public OrderDetails processRefund(
            @PathVariable int orderId,
            @RequestBody String refundAmountStr) {
        double refundAmount = Double.parseDouble(refundAmountStr);
        return orderServiceImpl.processRefund(orderId, refundAmount);
    }
}
