package com.asdc.dalexchange.service;

import com.asdc.dalexchange.model.OrderDetails;
import com.asdc.dalexchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    public List<OrderDetails> getOrdersByUserId(Long userId);

    public long newOrders();

    public double totalSales();

    public double avgSales();

}
