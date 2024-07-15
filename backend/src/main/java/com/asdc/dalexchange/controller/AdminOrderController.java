package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.model.OrderDetails;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    @Autowired
    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public List<OrderDetails> getOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/orderDetails/{orderId}")
    public OrderDetails getOrderById(@PathVariable int orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/update/{orderId}")
    public OrderDetails updateOrder(
            @PathVariable int orderId,
            @RequestBody OrderDetails updatedOrderDetails) {
        return orderService.updateOrder(orderId, updatedOrderDetails);
    }

    @PutMapping("/cancel/{orderId}")
    public void cancelOrder(
            @PathVariable int orderId,
            @RequestBody String adminComments) {
        orderService.cancelOrder(orderId, adminComments);
    }

    @PutMapping("/refund/{orderId}")
    public OrderDetails processRefund(
            @PathVariable int orderId,
            @RequestBody String refundAmountStr) {
        double refundAmount = Double.parseDouble(refundAmountStr);
        return orderService.processRefund(orderId, refundAmount);
    }
}
