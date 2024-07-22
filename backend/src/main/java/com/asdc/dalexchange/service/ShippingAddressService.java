package com.asdc.dalexchange.service;


import com.asdc.dalexchange.model.ShippingAddress;

import java.util.Optional;

public interface ShippingAddressService {

    public ShippingAddress saveShippingAddress(ShippingAddress address);

    public Optional<ShippingAddress> getShippingAddressById(Long id);
}
