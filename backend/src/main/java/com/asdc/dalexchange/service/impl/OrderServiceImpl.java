package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.enums.OrderStatus;
import com.asdc.dalexchange.model.OrderDetails;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.ShippingAddress;
import com.asdc.dalexchange.repository.OrderRepository;
import com.asdc.dalexchange.repository.ShippingRepository;
import com.asdc.dalexchange.service.OrderService;
import jakarta.transaction.Transactional;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShippingRepository shippingRepository;

    public List<OrderDetails> getAllOrders() {
        return orderRepository.findAll();
    }

    public double salesChange() {
        LocalDateTime now = getCurrentDateTime();
        LocalDateTime startOfCurrentPeriod = now.minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        Double totalSalesLast30Days = orderRepository.getTotalSalesSince(startOfCurrentPeriod);
        Double totalSalesPrevious30Days = orderRepository.getTotalSalesBetween(startOfPreviousPeriod, startOfCurrentPeriod);

        if (totalSalesLast30Days == null) {
            totalSalesLast30Days = 0.0;
        }
        if (totalSalesPrevious30Days == null) {
            totalSalesPrevious30Days = 0.0;
        }

        double percentageIncrease = calculatePercentageIncrease(totalSalesLast30Days, totalSalesPrevious30Days);

        return percentageIncrease;
    }

    public double ordersChange() {
        LocalDateTime now = getCurrentDateTime();
        LocalDateTime startOfCurrentPeriod = now.minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        Long totalOrdersLast30Days = orderRepository.countOrdersSince(startOfCurrentPeriod);
        Long totalOrdersPrevious30Days = orderRepository.countOrdersBetween(startOfPreviousPeriod, startOfCurrentPeriod);

        if (totalOrdersLast30Days == null) {
            totalOrdersLast30Days = 0L;
        }
        if (totalOrdersPrevious30Days == null) {
            totalOrdersPrevious30Days = 0L;
        }

        return calculatePercentageIncrease(totalOrdersLast30Days.doubleValue(), totalOrdersPrevious30Days.doubleValue());
    }

    private double calculatePercentageIncrease(Double currentSales, Double previousSales) {
        if (previousSales == 0) {
            return currentSales > 0 ? 100.0 : 0.0;
        }
        return ((currentSales - previousSales) / previousSales) * 100;
    }

    public double avgOrderValueChange() {
        LocalDateTime now = getCurrentDateTime();
        LocalDateTime startOfCurrentPeriod = now.minusDays(30);
        LocalDateTime startOfPreviousPeriod = startOfCurrentPeriod.minusDays(30);

        Double avgOrderValueLast30Days = orderRepository.getAvgOrderValueSince(startOfCurrentPeriod);
        Double avgOrderValuePrevious30Days = orderRepository.getAvgOrderValueBetween(startOfPreviousPeriod, startOfCurrentPeriod);

        if (avgOrderValueLast30Days == null) {
            avgOrderValueLast30Days = 0.0;
        }
        if (avgOrderValuePrevious30Days == null) {
            avgOrderValuePrevious30Days = 0.0;
        }

        return calculatePercentageIncrease(avgOrderValueLast30Days, avgOrderValuePrevious30Days);
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

    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }


    // Order Moderation
    public OrderDetails getOrderById(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Transactional
    public OrderDetails updateOrder(int orderId, OrderDetails updatedOrderDetails) {
        OrderDetails order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (updatedOrderDetails.getTotalAmount() != 0) {
            order.setTotalAmount(updatedOrderDetails.getTotalAmount());
        }
        if (updatedOrderDetails.getOrderStatus() != null) {
            order.setOrderStatus(updatedOrderDetails.getOrderStatus());
        }
        if (updatedOrderDetails.getAdminComments() != null) {
            order.setAdminComments(updatedOrderDetails.getAdminComments());
        }
        if (updatedOrderDetails.getShippingAddress() != null) {
            updateShippingAddress(order.getShippingAddress().getAddressId(), updatedOrderDetails.getShippingAddress());
        }
        if (updatedOrderDetails.getPayment() != null) {
            order.setPayment(updatedOrderDetails.getPayment());
        }
        return orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(int orderId, String adminComments) {
        OrderDetails order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(OrderStatus.CANCELLED);
        order.setAdminComments(adminComments);
        orderRepository.save(order);
    }

    @Transactional
    public OrderDetails processRefund(int orderId, double refundAmount) {
        OrderDetails order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setTotalAmount(order.getTotalAmount() - refundAmount);
        return orderRepository.save(order);
    }

    @Transactional
    public void updateShippingAddress(Long addressId, ShippingAddress updatedShippingAddress) {
        ShippingAddress existingAddress = shippingRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Shipping Address not found"));

        existingAddress.setBillingName(updatedShippingAddress.getBillingName());
        existingAddress.setCountry(updatedShippingAddress.getCountry());
        existingAddress.setLine1(updatedShippingAddress.getLine1());
        existingAddress.setLine2(updatedShippingAddress.getLine2());
        existingAddress.setCity(updatedShippingAddress.getCity());
        existingAddress.setState(updatedShippingAddress.getState());
        existingAddress.setPostalCode(updatedShippingAddress.getPostalCode());

        shippingRepository.save(existingAddress);
    }



}
