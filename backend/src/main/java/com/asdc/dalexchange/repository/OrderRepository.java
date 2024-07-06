package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetails, Integer> {

    @Query(value = "SELECT COUNT(*) FROM order_details WHERE transaction_datetime >= CURDATE() - INTERVAL 30 DAY", nativeQuery = true)
    Long countOrdersInLast30Days();

    @Query(value = "SELECT SUM(total_amount) FROM order_details WHERE transaction_datetime >= CURDATE() - INTERVAL 30 DAY", nativeQuery = true)
    double totalSalesInLast30Days();

    @Query(value = "SELECT AVG(total_amount) FROM order_details WHERE transaction_datetime >= CURDATE() - INTERVAL 30 DAY", nativeQuery = true)
    double avgOrderValueInLast30Days();

    @Query("SELECT SUM(od.totalAmount) FROM OrderDetails od WHERE od.transactionDatetime >= :startDate")
    Double getTotalSalesSince(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT SUM(od.totalAmount) FROM OrderDetails od WHERE od.transactionDatetime >= :startDate AND od.transactionDatetime < :endDate")
    Double getTotalSalesBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // order
    @Query("SELECT COUNT(o) FROM OrderDetails o WHERE o.transactionDatetime >= :startDate")
    Long countOrdersSince(LocalDateTime startDate);

    @Query("SELECT COUNT(o) FROM OrderDetails o WHERE o.transactionDatetime >= :startDate AND o.transactionDatetime < :endDate")
    Long countOrdersBetween(LocalDateTime startDate, LocalDateTime endDate);

    // order value
    @Query("SELECT AVG(o.totalAmount) FROM OrderDetails o WHERE o.transactionDatetime >= :startDate")
    Double getAvgOrderValueSince(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT AVG(o.totalAmount) FROM OrderDetails o WHERE o.transactionDatetime >= :startDate AND o.transactionDatetime < :endDate")
    Double getAvgOrderValueBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
