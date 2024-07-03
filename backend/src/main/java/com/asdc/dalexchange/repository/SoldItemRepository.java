package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.SoldItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SoldItemRepository extends JpaRepository<SoldItem,Integer> {


    @Query("SELECT si FROM SoldItem si JOIN si.product p JOIN p.seller s WHERE s.userId = :userId")
    List<SoldItem> findAllSoldItemsBySellerUserId(Long userId);

    /// List<SoldItem> findBySellerUserId(Long userId);
}
