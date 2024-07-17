package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.dto.SoldItemDTO;
import com.asdc.dalexchange.mappers.impl.SoldItemMapperImpl;
import com.asdc.dalexchange.model.SoldItem;
import com.asdc.dalexchange.repository.SoldItemRepository;
import com.asdc.dalexchange.service.SoldItemService;
import com.asdc.dalexchange.specifications.SoldItemSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoldItemServiceImpl implements SoldItemService {

    @Autowired
    private SoldItemRepository soldItemRepository;

    @Autowired
    private SoldItemMapperImpl soldItemMapper;

    @Override
    public List<SoldItemDTO> GetallSoldProduct(Long userid) {
        List<SoldItem> allSoldItems = soldItemRepository.findAll(SoldItemSpecification.bySellerUserId(userid));
        return allSoldItems.stream()
                .map(soldItemMapper::mapTo)
                .collect(Collectors.toList());
    }

}
