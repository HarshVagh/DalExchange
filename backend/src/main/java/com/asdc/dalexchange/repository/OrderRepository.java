package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetails, Integer> {

    @Query(value = "SELECT COUNT(*) FROM order_details WHERE transaction_datetime >= CURDATE() - INTERVAL 30 DAY", nativeQuery = true)
    Long countOrdersInLast30Days();

    @Query(value = "SELECT SUM(total_amount) FROM order_details WHERE transaction_datetime >= CURDATE() - INTERVAL 30 DAY", nativeQuery = true)
    double totalSalesInLast30Days();

    @Query(value = "SELECT AVG(total_amount) FROM order_details WHERE transaction_datetime >= CURDATE() - INTERVAL 30 DAY", nativeQuery = true)
    double avgOrderValueInLast30Days();
}
