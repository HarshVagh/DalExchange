package com.asdc.dalexchange.service;

import com.asdc.dalexchange.repository.OrderRepository;
import com.asdc.dalexchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public long newOrders(){
        return orderRepository.countOrdersInLast30Days();
    }

    public double totalSales(){
        return orderRepository.totalSalesInLast30Days();
    }

    public double avgSales(){
        return orderRepository.avgOrderValueInLast30Days();
    }

}
