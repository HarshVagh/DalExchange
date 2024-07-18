package com.asdc.dalexchange.service;

import com.asdc.dalexchange.model.Notification;

import java.util.List;

public interface NotificationService {
    void sendNotification(Notification notification);
    List<Notification> getNotifications(Long userId);
    Notification markNotificationAsRead(Long notificationId);
}
