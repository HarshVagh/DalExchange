package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.SoldItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoldItemRepository extends JpaRepository<SoldItem,Integer> {

    List<SoldItem> findBySellerUserId(Long userId);
}
