package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.OrderDTO;
import com.asdc.dalexchange.dto.UserDTO;
import com.asdc.dalexchange.enums.OrderStatus;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.OrderDetails;
import com.asdc.dalexchange.model.ShippingAddress;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.OrderRepository;
import com.asdc.dalexchange.repository.ShippingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ShippingRepository shippingRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private Mapper<OrderDetails, OrderDTO> orderMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalesChange() {
        LocalDateTime now = LocalDateTime.now();
        when(orderRepository.getTotalSalesSince(any(LocalDateTime.class))).thenReturn(1000.0);
        when(orderRepository.getTotalSalesBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(800.0);

        double result = orderService.salesChange();

        assertEquals(25.0, result);

        // Additional checks to ensure line coverage for null values
        when(orderRepository.getTotalSalesSince(any(LocalDateTime.class))).thenReturn(null);
        when(orderRepository.getTotalSalesBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(null);

        result = orderService.salesChange();
        assertEquals(0.0, result);
    }

    @Test
    void testOrdersChange() {
        LocalDateTime now = LocalDateTime.now();
        when(orderRepository.countOrdersSince(any(LocalDateTime.class))).thenReturn(50L);
        when(orderRepository.countOrdersBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(40L);

        double result = orderService.ordersChange();

        assertEquals(25.0, result);

        // Additional checks to ensure line coverage for null values
        when(orderRepository.countOrdersSince(any(LocalDateTime.class))).thenReturn(null);
        when(orderRepository.countOrdersBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(null);

        result = orderService.ordersChange();
        assertEquals(0.0, result);
    }

    @Test
    void testAvgOrderValueChange() {
        LocalDateTime now = LocalDateTime.now();
        when(orderRepository.getAvgOrderValueSince(any(LocalDateTime.class))).thenReturn(20.0);
        when(orderRepository.getAvgOrderValueBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(16.0);

        double result = orderService.avgOrderValueChange();

        assertEquals(25.0, result);

        when(orderRepository.getAvgOrderValueSince(any(LocalDateTime.class))).thenReturn(null);
        when(orderRepository.getAvgOrderValueBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(null);

        result = orderService.avgOrderValueChange();
        assertEquals(0.0, result);
    }

    @Test
    void testNewOrders() {
        when(orderRepository.countOrdersInLast30Days()).thenReturn(100L);

        long result = orderService.newOrders();

        assertEquals(100, result);
    }

    @Test
    void testTotalSales() {
        when(orderRepository.totalSalesInLast30Days()).thenReturn(5000.0);

        double result = orderService.totalSales();

        assertEquals(5000.0, result);
    }

    @Test
    void testAvgSales() {
        when(orderRepository.avgOrderValueInLast30Days()).thenReturn(50.0);

        double result = orderService.avgSales();

        assertEquals(50.0, result);
    }

    @Test
    public void testGetAllOrders() {
        // Arrange
        OrderDetails order1 = new OrderDetails();
        order1.setOrderId(1L);
        order1.setTotalAmount(100.0);

        OrderDetails order2 = new OrderDetails();
        order2.setOrderId(2L);
        order2.setTotalAmount(200.0);

        List<OrderDetails> orders = Arrays.asList(order1, order2);
        List<OrderDTO> orderDTOs = orders.stream().map(order -> {
            OrderDTO dto = new OrderDTO();
            dto.setOrderId(order.getOrderId());
            dto.setTotalAmount(order.getTotalAmount());
            return dto;
        }).collect(Collectors.toList());

        when(orderRepository.findAll()).thenReturn(orders);
        when(orderMapper.mapTo(order1)).thenReturn(orderDTOs.get(0));
        when(orderMapper.mapTo(order2)).thenReturn(orderDTOs.get(1));

        // Act
        List<OrderDTO> result = orderService.getAllOrders();

        // Assert
        assertEquals(2, result.size());
        assertEquals(100.0, result.get(0).getTotalAmount());
        assertEquals(200.0, result.get(1).getTotalAmount());
    }

    @Test
    public void testGetOrderById() {
        // Arrange
        long orderId = 1L;
        OrderDetails order = new OrderDetails();
        order.setOrderId(orderId);
        order.setTotalAmount(100.0);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(orderId);
        orderDTO.setTotalAmount(100.0);

        when(orderRepository.findById((int) orderId)).thenReturn(Optional.of(order));
        when(orderMapper.mapTo(order)).thenReturn(orderDTO);

        // Act
        OrderDTO result = orderService.getOrderById((int) orderId);

        // Assert
        assertEquals(orderDTO, result);
    }

    @Test
    public void testUpdateOrder() {
        // Arrange
        long orderId = 1L;
        OrderDetails existingOrder = new OrderDetails();
        existingOrder.setOrderId(orderId);
        existingOrder.setTotalAmount(100.0);

        OrderDetails updatedOrderDetails = new OrderDetails();
        updatedOrderDetails.setTotalAmount(150.0);
        updatedOrderDetails.setOrderStatus(OrderStatus.SHIPPED);

        OrderDTO updatedOrderDTO = new OrderDTO();
        updatedOrderDTO.setOrderId(orderId);
        updatedOrderDTO.setTotalAmount(150.0);

        when(orderRepository.findById((int) orderId)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(existingOrder)).thenReturn(existingOrder);
        when(orderMapper.mapTo(existingOrder)).thenReturn(updatedOrderDTO);

        // Act
        OrderDTO result = orderService.updateOrder((int) orderId, updatedOrderDetails);

        // Assert
        assertEquals(150.0, existingOrder.getTotalAmount());
        assertEquals(OrderStatus.SHIPPED, existingOrder.getOrderStatus());
        assertEquals(updatedOrderDTO, result);
    }

    @Test
    public void testCancelOrder() {
        // Arrange
        long orderId = 1L;
        OrderDetails order = new OrderDetails();
        order.setOrderId(orderId);
        order.setOrderStatus(OrderStatus.SHIPPED);

        when(orderRepository.findById((int) orderId)).thenReturn(Optional.of(order));

        // Act
        orderService.cancelOrder((int) orderId, "Cancelled by admin");

        // Assert
        assertEquals(OrderStatus.CANCELLED, order.getOrderStatus());
        assertEquals("Cancelled by admin", order.getAdminComments());
        verify(orderRepository).save(order);
    }

    @Test
    public void testProcessRefund() {
        // Arrange
        long orderId = 1L;
        OrderDetails order = new OrderDetails();
        order.setOrderId(orderId);
        order.setTotalAmount(200.0);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(orderId);
        orderDTO.setTotalAmount(150.0);

        when(orderRepository.findById(Math.toIntExact(orderId))).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.mapTo(order)).thenReturn(orderDTO);

        // Act
        OrderDTO result = orderService.processRefund((int) orderId, 50.0);

        // Assert
        assertEquals(150.0, order.getTotalAmount());
        assertEquals(orderDTO, result);
    }

    @Test
    public void testUpdateShippingAddress() {
        // Arrange
        long addressId = 1L;
        ShippingAddress existingAddress = new ShippingAddress();
        existingAddress.setAddressId(addressId);
        existingAddress.setBillingName("Old Name");

        ShippingAddress updatedAddress = new ShippingAddress();
        updatedAddress.setBillingName("New Name");

        when(shippingRepository.findById(addressId)).thenReturn(Optional.of(existingAddress));

        // Act
        orderService.updateShippingAddress(addressId, updatedAddress);

        // Assert
        assertEquals("New Name", existingAddress.getBillingName());
        verify(shippingRepository).save(existingAddress);
    }

    @Test
    public void testUpdateOrder_ShippingAddress() {
        // Arrange
        long orderId = 1L;
        OrderDetails existingOrder = new OrderDetails();
        existingOrder.setOrderId(orderId);

        ShippingAddress existingAddress = new ShippingAddress();
        existingAddress.setAddressId(1L);
        existingAddress.setBillingName("Old Name");

        existingOrder.setShippingAddress(existingAddress);

        ShippingAddress updatedAddress = new ShippingAddress();
        updatedAddress.setAddressId(1L);
        updatedAddress.setBillingName("New Name");

        OrderDetails updatedOrderDetails = new OrderDetails();
        updatedOrderDetails.setShippingAddress(updatedAddress);

        OrderDTO updatedOrderDTO = new OrderDTO();
        updatedOrderDTO.setOrderId(orderId);

        when(orderRepository.findById((int) orderId)).thenReturn(Optional.of(existingOrder));
        when(shippingRepository.findById(existingAddress.getAddressId())).thenReturn(Optional.of(existingAddress));
        when(orderRepository.save(existingOrder)).thenReturn(existingOrder);
        when(orderMapper.mapTo(existingOrder)).thenReturn(updatedOrderDTO);

        // Act
        OrderDTO result = orderService.updateOrder((int) orderId, updatedOrderDetails);

        // Assert
        assertEquals("New Name", existingAddress.getBillingName());
        assertEquals(updatedOrderDTO, result);
    }

    @Test
    public void testUpdateShippingAddress_NotFound() {
        // Arrange
        long addressId = 1L;
        ShippingAddress updatedAddress = new ShippingAddress();

        when(shippingRepository.findById(addressId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.updateShippingAddress(addressId, updatedAddress);
        });

        assertEquals("Shipping Address not found", exception.getMessage());
    }
}
