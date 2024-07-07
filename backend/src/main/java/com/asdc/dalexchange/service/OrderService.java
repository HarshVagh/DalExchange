package com.asdc.dalexchange.service;

import com.asdc.dalexchange.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

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

}
