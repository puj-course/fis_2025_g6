package com.fis_2025_g6;

import com.fis_2025_g6.service.NotificationService;
import com.fis_2025_g6.notification.Notifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private Notifier mockNotifier; 

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
      
        notificationService = new NotificationService(mockNotifier);
    }

    @Test
    void send_ShouldDelegateToNotifier() {
        String testMessage = "Test notification";
        String testRecipient = "test@example.com";
        
        notificationService.send(testMessage, testRecipient);
        
        verify(mockNotifier, times(1)).send(testMessage, testRecipient);
    }

    @Test
    void send_ShouldHandleNullMessage() {
        String testRecipient = "test@example.com";
        
        notificationService.send(null, testRecipient);
        verify(mockNotifier, times(1)).send(null, testRecipient);
    }

    @Test
    void send_ShouldHandleEmptyMessage() {
        String testMessage = "";
        String testRecipient = "test@example.com";
        
        notificationService.send(testMessage, testRecipient);
        
        verify(mockNotifier, times(1)).send(testMessage, testRecipient);
    }

    @Test
    void send_ShouldHandleNullRecipient() {
        String testMessage = "Test notification";
        
        notificationService.send(testMessage, null);
        verify(mockNotifier, times(1)).send(testMessage, null);
    }

    @Test
    void send_ShouldHandleEmptyRecipient() {
        String testMessage = "Test notification";
        String testRecipient = "";
        
        notificationService.send(testMessage, testRecipient);
        
        verify(mockNotifier, times(1)).send(testMessage, testRecipient);
    }
}