package com.asdc.dalexchange.controller;

import com.asdc.dalexchange.dto.NotificationDTO;
import com.asdc.dalexchange.mappers.Mapper;
import com.asdc.dalexchange.model.Notification;
import com.asdc.dalexchange.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NotificationControllerTest {

    @InjectMocks
    private NotificationController notificationController;

    @Mock
    private NotificationService notificationService;

    @Mock
    private Mapper<Notification, NotificationDTO> notificationMapper;

    @Test
    public void testGetNotifications() {
        Notification notification = new Notification();
        NotificationDTO notificationDTO = new NotificationDTO();
        List<Notification> notifications = List.of(notification);
        List<NotificationDTO> notificationDTOs = List.of(notificationDTO);

        when(notificationService.getNotifications(1L)).thenReturn(notifications);
        when(notificationMapper.mapTo(notification)).thenReturn(notificationDTO);

        List<NotificationDTO> result = notificationController.getNotifications();

        assertEquals(notificationDTOs, result);
        verify(notificationService).getNotifications(1L);
        verify(notificationMapper).mapTo(notification);
    }

    @Test
    public void testMarkAsRead() {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setIsRead(true);

        when(notificationService.markNotificationAsRead(1L)).thenReturn(notification);

        ResponseEntity<Notification> response = notificationController.markAsRead(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notification, response.getBody());
        verify(notificationService).markNotificationAsRead(1L);
    }
}
