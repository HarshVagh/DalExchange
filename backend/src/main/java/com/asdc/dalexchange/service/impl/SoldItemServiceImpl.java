package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.SoldItemDTO;
import com.asdc.dalexchange.mappers.impl.SoldItemMapperImpl;
import com.asdc.dalexchange.model.Product;
import com.asdc.dalexchange.model.SoldItem;
import com.asdc.dalexchange.model.User;
import com.asdc.dalexchange.repository.ProductRepository;
import com.asdc.dalexchange.repository.SoldItemRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.SoldItemService;
import com.asdc.dalexchange.specifications.SoldItemSpecification;
import com.asdc.dalexchange.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SoldItemServiceImpl implements SoldItemService {

    @Autowired
    private SoldItemRepository soldItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SoldItemMapperImpl soldItemMapper;

    @Override
    public List<SoldItemDTO> GetallSoldProduct() {
        Long userId = AuthUtil.getCurrentUserId(userRepository);
        List<SoldItem> allSoldItems = soldItemRepository.findAll(SoldItemSpecification.bySellerUserId(userId));
        return allSoldItems.stream()
                .map(soldItemMapper::mapTo)
                .collect(Collectors.toList());
    }

    public void saveSoldItem(Map<String, Object> requestBody) {
        Long productId = Long.parseLong(requestBody.get("productId").toString());

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        boolean exists = soldItemRepository.existsByProduct_ProductId(productId);
        if (exists) {
            System.out.println("SoldItem for productId " + productId + " already exists. Skipping save.");
            return;
        }

        User seller = product.getSeller();
        SoldItem soldItem = new SoldItem();
        soldItem.setSeller(seller);
        soldItem.setProduct(product);
        soldItem.setSoldDate(LocalDateTime.now());

        try {
            soldItemRepository.save(soldItem);
            System.out.println("SoldItem saved successfully.");
        } catch (Exception e) {
            System.err.println("Error saving SoldItem: " + e.getMessage());
            throw new RuntimeException("Error saving SoldItem", e);
        }
    }


}
