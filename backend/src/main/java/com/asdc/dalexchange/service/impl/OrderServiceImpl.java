package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.*;
import com.asdc.dalexchange.enums.OrderStatus;
import com.asdc.dalexchange.enums.PaymentStatus;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.*;
import com.asdc.dalexchange.repository.*;
import com.asdc.dalexchange.service.OrderService;
import com.asdc.dalexchange.service.TradeRequestService;
import com.asdc.dalexchange.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TradeRequestService tradeRequestService;

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private Mapper<OrderDetails, OrderDTO> orderMapper;

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


    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::mapTo)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(int orderId) {
        OrderDetails order = orderRepository.findById((long) orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.mapTo(order);
    }

    @Transactional
    public OrderDTO updateOrder(int orderId, OrderDetails updatedOrderDetails) {
        OrderDetails order = orderRepository.findById((long) orderId)
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
        OrderDetails updatedOrder = orderRepository.save(order);
        return orderMapper.mapTo(updatedOrder);
    }

    @Transactional
    public void cancelOrder(int orderId, String adminComments) {
        OrderDetails order = orderRepository.findById((long) orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(OrderStatus.Cancelled);
        order.setAdminComments(adminComments);
        orderRepository.save(order);
    }

    @Transactional
    public OrderDTO processRefund(int orderId, double refundAmount) {
        OrderDetails order = orderRepository.findById((long) orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setTotalAmount(order.getTotalAmount() - refundAmount);
        OrderDetails updatedOrder = orderRepository.save(order);
        return orderMapper.mapTo(updatedOrder);
    }

    @Transactional
    @Override
    public Long saveNewOrder(ShippingAddress getSavedShippingAddress, Long productId) {
        log.info("Saving new order for productId: {}", productId);

        Long userId = AuthUtil.getCurrentUserId(userRepository);
        Double amount = tradeRequestService.getApprovedTradeRequestAmount(productId);

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            log.error("Product with id {} not found", productId);
            throw new RuntimeException("Product not found");
        }

        Product product = optionalProduct.get();
        product.setSold(true);
        productRepository.save(product);
        log.info("Product with id {} marked as sold", productId);

        User user = userRepository.findByUserId(userId);
        if (user == null) {
            log.error("User with id {} not found", userId);
            throw new RuntimeException("User not found");
        }

        Payment payment = new Payment();
        payment.setPaymentMethod("Card");
        payment.setPaymentStatus(PaymentStatus.completed);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDateTime.now());
        Payment savedPayment = paymentRepository.save(payment);
        log.info("Payment saved with id: {}", savedPayment.getPaymentId());

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setBuyer(user);
        orderDetails.setProductId(product);
        orderDetails.setTotalAmount(amount);
        orderDetails.setOrderStatus(OrderStatus.Pending);
        orderDetails.setTransactionDatetime(LocalDateTime.now());
        orderDetails.setShippingAddress(getSavedShippingAddress);
        orderDetails.setPayment(savedPayment);

        OrderDetails savedOrder = orderRepository.save(orderDetails);
        log.info("Order saved with id: {}", savedOrder.getOrderId());

        return savedOrder.getOrderId();
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


    @Override
    public List<ItemsSoldDTO> getItemsSold() {
        List<Object[]> results = orderRepository.findItemsSoldPerMonth();
        return results.stream().map(result -> {
            ItemsSoldDTO dto = new ItemsSoldDTO();
            dto.setMonth((String) result[0]);
            dto.setItemsSold(((Number) result[1]).intValue());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<TopSellingCategoriesDTO> getTopSellingCategories() {
        List<Object[]> results = orderRepository.findTopSellingCategories();
        return results.stream().map(result -> {
            TopSellingCategoriesDTO dto = new TopSellingCategoriesDTO();
            dto.setCategory((String) result[0]);
            dto.setSales(((Number) result[1]).intValue());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<BestSellingProductsDTO> getBestSellingProducts() {
        List<Object[]> results = orderRepository.findBestSellingProducts();
        return results.stream().map(result -> {
            BestSellingProductsDTO dto = new BestSellingProductsDTO();
            dto.setProductName((String) result[0]);
            dto.setItemsSold(((Number) result[1]).intValue());
            return dto;
        }).collect(Collectors.toList());
    }

}
