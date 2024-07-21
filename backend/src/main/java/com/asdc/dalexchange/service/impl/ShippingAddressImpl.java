package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.model.ShippingAddress;
import com.asdc.dalexchange.repository.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingAddressImpl {

    @Autowired
    ShippingRepository shippingRepository;

    public ShippingAddress save(ShippingAddress shippingAddress){
        return shippingRepository.save(shippingAddress);
    }
}
