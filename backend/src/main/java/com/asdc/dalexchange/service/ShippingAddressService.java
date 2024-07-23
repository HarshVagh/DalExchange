package com.asdc.dalexchange.service;


import com.asdc.dalexchange.model.ShippingAddress;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ShippingAddressService {

    ShippingAddress saveShippingAddress(ShippingAddress address);

    Optional<ShippingAddress> getShippingAddressById(Long id);
}
