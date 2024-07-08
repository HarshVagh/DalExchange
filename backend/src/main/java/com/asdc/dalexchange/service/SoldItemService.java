package com.asdc.dalexchange.service;

import com.asdc.dalexchange.model.SoldItem;

import java.util.List;

public interface SoldItemService {
    public List<SoldItem> getSoldItemsBySellerId(Long sellerId);
}