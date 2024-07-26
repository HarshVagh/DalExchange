package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.model.ShippingAddress;
import com.asdc.dalexchange.repository.ShippingRepository;
import com.asdc.dalexchange.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ShippingAddressImpl implements ShippingAddressService {

    @Autowired
    ShippingRepository shippingRepository;

    // store the address
    @Override
    public ShippingAddress saveShippingAddress(Map<String,Object> requestBody) {
             ShippingAddress address = new ShippingAddress();
                address.setBillingName(requestBody.get("billingName").toString());
                address.setCountry(requestBody.get("country").toString());
                address.setLine1(requestBody.get("line1").toString());
                address.setLine2(requestBody.get("line2") != null ? requestBody.get("line2").toString() : null);
                address.setCity(requestBody.get("city").toString());
                address.setState(requestBody.get("state").toString());
                address.setPostalCode(requestBody.get("postalCode").toString());
                return shippingRepository.save(address);
    }

}
