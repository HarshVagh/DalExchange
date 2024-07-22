package com.asdc.dalexchange.mappers.impl;

import com.asdc.dalexchange.dto.NotificationDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Notification;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapperImpl implements Mapper<Notification, NotificationDTO> {

    private final ModelMapper modelMapper;

    public NotificationMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public NotificationDTO mapTo(Notification notification) {
        return modelMapper.map(notification, NotificationDTO.class);
    }

    @Override
    public Notification mapFrom(NotificationDTO notificationDTO) {
        return modelMapper.map(notificationDTO, Notification.class);
    }
}
