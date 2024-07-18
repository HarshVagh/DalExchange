package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.NotificationDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Notification;
import com.asdc.dalexchange.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private NotificationService notificationService;
    private Mapper<Notification, NotificationDTO> notificationMapper;

    public NotificationController(
            NotificationService notificationService,
            Mapper<Notification, NotificationDTO> notificationMapper) {
        this.notificationService = notificationService;
        this.notificationMapper = notificationMapper;
    }

    @GetMapping("")
    public List<NotificationDTO> getNotifications() {
        List<Notification> notifications = notificationService.getNotifications();
        return notifications.stream()
                .map(notificationMapper::mapTo)
                .collect(Collectors.toList());
    }

    @PutMapping("/mark/{id}")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        Notification notification = notificationService.markNotificationAsRead(id);
        return ResponseEntity.ok(notification);
    }
}

