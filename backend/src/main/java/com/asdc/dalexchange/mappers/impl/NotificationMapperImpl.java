package com.asdc.dalexchange.mappers.impl;

import com.asdc.dalexchange.dto.NotificationDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Notification;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotificationMapperImpl implements Mapper<Notification, NotificationDTO> {

    private final ModelMapper modelMapper;

    @Override
    public NotificationDTO mapTo(Notification notification) {
        return modelMapper.map(notification, NotificationDTO.class);
    }

    @Override
    public Notification mapFrom(NotificationDTO notificationDTO) {
        return modelMapper.map(notificationDTO, Notification.class);
    }
}
