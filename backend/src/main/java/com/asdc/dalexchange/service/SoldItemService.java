package com.asdc.dalexchange.service;

import com.asdc.dalexchange.dto.SoldItemDTO;

import java.util.List;
import java.util.Map;

public interface SoldItemService {
    List<SoldItemDTO> GetallSoldProduct();
    void  saveSoldItem(Map<String,Object> requestBody);
}