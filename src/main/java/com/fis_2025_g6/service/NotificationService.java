package com.fis_2025_g6.service;

import com.fis_2025_g6.notification.*;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final Notifier notifier;

    public NotificationService(@Qualifier("smsNotifier") Notifier notifier) {
        this.notifier = notifier;
    }

    public void send(String message, String recipient) {
        notifier.send(message, recipient);
    }
}
