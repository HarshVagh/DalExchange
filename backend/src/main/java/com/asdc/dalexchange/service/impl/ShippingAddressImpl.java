package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.model.ShippingAddress;
import com.asdc.dalexchange.repository.ShippingRepository;
import com.asdc.dalexchange.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShippingAddressImpl implements ShippingAddressService {

    @Autowired
    ShippingRepository shippingRepository;

    // store the address
    @Override
    public ShippingAddress saveShippingAddress(ShippingAddress address) {
        return shippingRepository.save(address);
    }


    // gate address of the from address id
    @Override
    public Optional<ShippingAddress> getShippingAddressById(Long id) {
        return shippingRepository.findById(id);
    }
}
