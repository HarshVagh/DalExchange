/*
package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.model.OrderDetails;
import com.asdc.dalexchange.repository.OrderRepository;
import com.asdc.dalexchange.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;


    public void orderRepository(OrderRepository orderRepository) {

        this.orderRepository = orderRepository;
    }

    @Override
    public double salesChange() {
        return 0;
    }

    @Override
    public double ordersChange() {
        return 0;
    }

    @Override
    public double avgOrderValueChange() {
        return 0;
    }

    @Override
    public long newOrders(){
        return orderRepository.countOrdersInLast30Days();
    }

    @Override
    public double totalSales(){
        return orderRepository.totalSalesInLast30Days();
    }

    @Override
    public double avgSales(){
        return orderRepository.avgOrderValueInLast30Days();
    }

    @Override
    public LocalDateTime getCurrentDateTime() {
        return null;
    }

    @Override
    public OrderDetails getOrderById(int orderId) {
        return null;
    }

    @Override
    public OrderDetails updateOrder(int orderId, OrderDetails updatedOrderDetails) {
        return null;
    }

    @Override
    public void cancelOrder(int orderId, String adminComments) {

    }

    @Override
    public OrderDetails processRefund(int orderId, double refundAmount) {
        return null;
    }

}
*/
