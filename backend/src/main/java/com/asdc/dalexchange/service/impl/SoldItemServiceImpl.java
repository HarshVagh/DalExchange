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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SoldItemServiceImpl implements SoldItemService {

    private final SoldItemRepository soldItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SoldItemMapperImpl soldItemMapper;

    @Override
    public List<SoldItemDTO> GetallSoldProduct() {
        Long userId = AuthUtil.getCurrentUserId(userRepository);
        log.info("Fetching all sold products for userId: {}", userId);

        List<SoldItem> allSoldItems = soldItemRepository.findAll(SoldItemSpecification.bySellerUserId(userId));
        List<SoldItemDTO> soldItemDTOs = allSoldItems.stream()
                .map(soldItemMapper::mapTo)
                .collect(Collectors.toList());

        log.info("Fetched {} sold products for userId: {}", soldItemDTOs.size(), userId);
        return soldItemDTOs;
    }

    public void saveSoldItem(Map<String, Object> requestBody) {
        Long productId = Long.parseLong(requestBody.get("productId").toString());
        log.info("Attempting to save sold item for productId: {}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", productId);
                    return new RuntimeException("Product not found");
                });

        boolean exists = soldItemRepository.existsByProduct_ProductId(productId);
        if (exists) {
            log.info("SoldItem for productId {} already exists. Skipping save.", productId);
            return;
        }

        User seller = product.getSeller();
        SoldItem soldItem = new SoldItem();
        soldItem.setSeller(seller);
        soldItem.setProduct(product);
        soldItem.setSoldDate(LocalDateTime.now());

        try {
            soldItemRepository.save(soldItem);
            log.info("SoldItem successfully saved for productId: {}", productId);
        } catch (Exception e) {
            log.error("Error saving SoldItem for productId: {}. Error: {}", productId, e.getMessage());
            throw new RuntimeException("Error saving SoldItem", e);
        }
    }
}
