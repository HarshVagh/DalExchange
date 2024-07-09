package com.asdc.dalexchange.service.imp;

import com.asdc.dalexchange.model.SoldItem;
import com.asdc.dalexchange.repository.SoldItemRepository;
import com.asdc.dalexchange.service.SoldItemService;
import com.asdc.dalexchange.specifications.SoldItemSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SoldItemServiceImp implements SoldItemService {

    @Autowired
    private SoldItemRepository soldItemRepository;

    @Override
    public List<SoldItem> getSoldItemsBySellerId(Long sellerId) {
        return soldItemRepository.findAll(SoldItemSpecification.bySellerUserId(sellerId));
    }
}
