package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.SoldItemDTO;
import com.asdc.dalexchange.model.SoldItem;

import java.util.List;

public interface SoldItemService {
    public List<SoldItemDTO> GetallSoldProduct(Long userid);
}