package com.asdc.dalexchange.service.impl;

import com.asdc.dalexchange.model.Notification;
import com.asdc.dalexchange.repository.NotificationRepository;
import com.asdc.dalexchange.service.NotificationService;
import com.asdc.dalexchange.specifications.NotificationSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;
    private JavaMailSender mailSender;

    public NotificationServiceImpl(NotificationRepository notificationRepository, JavaMailSender mailSender) {
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender;
    }

    public void sendNotification(Notification notification) {
        notificationRepository.save(notification);
        sendEmailNotification(notification);
    }

    private void sendEmailNotification(Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getUser().getEmail());
        message.setSubject("DalExchange Notification");
        message.setText(notification.getMessage());
        mailSender.send(message);
    }

    public List<Notification> getNotifications(Long userId) {
        Specification<Notification> spec = Specification.where(NotificationSpecification.hasUserId(userId));
        return notificationRepository.findAll(spec);
    }

    public Notification markNotificationAsRead(Long notificationId) {
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notification.setIsRead(true);
            return notificationRepository.save(notification);
        } else {
            throw new RuntimeException("Notification not found with id " + notificationId);
        }
    }
}
