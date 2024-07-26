package com.asdc.dalexchange.mappers.impl;

import com.asdc.dalexchange.dto.PaymentDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Payment;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapperImpl implements Mapper<Payment, PaymentDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public PaymentMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        TypeMap<Payment, PaymentDTO> propertyMapper = this.modelMapper.createTypeMap(Payment.class, PaymentDTO.class);
    }

    @Override
    public PaymentDTO mapTo(Payment payment) {
        return this.modelMapper.map(payment, PaymentDTO.class);
    }

    @Override
    public Payment mapFrom(PaymentDTO paymentDTO) {
        return this.modelMapper.map(paymentDTO, Payment.class);
    }
}
