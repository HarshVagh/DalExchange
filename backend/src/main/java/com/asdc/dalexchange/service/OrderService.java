package com.asdc.dalexchange.service;

import com.asdc.dalexchange.model.OrderDetails;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    double salesChange();
    double ordersChange();
    double avgOrderValueChange();
    long newOrders();
    double totalSales();
    double avgSales();
    LocalDateTime getCurrentDateTime();

    // Order Moderation
    List<OrderDetails> getAllOrders();
    OrderDetails getOrderById(int orderId);
    OrderDetails updateOrder(int orderId, OrderDetails updatedOrderDetails);
    void cancelOrder(int orderId, String adminComments);
    OrderDetails processRefund(int orderId, double refundAmount);
}
