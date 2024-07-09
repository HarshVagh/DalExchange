package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.SoldItem;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SoldItemRepository extends JpaRepository<SoldItem, Integer>, JpaSpecificationExecutor<SoldItem> {

    // Method using the specification
    List<SoldItem> findAll(Specification<SoldItem> specification);

}
























/*
package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.SoldItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SoldItemRepository extends JpaRepository<SoldItem,Integer> {


    @Query("SELECT si FROM SoldItem si JOIN si.product p JOIN p.seller s WHERE s.userId = :userId")
    public List<SoldItem> findAllSoldItemsBySellerUserId(Long userId);

}*/
