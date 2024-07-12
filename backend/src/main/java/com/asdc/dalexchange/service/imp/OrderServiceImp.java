package com.asdc.dalexchange.service.imp;

import com.asdc.dalexchange.model.OrderDetails;
import com.asdc.dalexchange.repository.OrderRepository;
import com.asdc.dalexchange.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.asdc.dalexchange.repository.OrderRepository;
import com.asdc.dalexchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;


    public void orderRepository(OrderRepository orderRepository) {

        this.orderRepository = orderRepository;
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

}
