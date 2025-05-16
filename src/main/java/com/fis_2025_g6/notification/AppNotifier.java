package com.fis_2025_g6.notification;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("appNotifier")
public class AppNotifier extends NotifierDecorator {
    public AppNotifier(@Qualifier("emailNotifier") Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message, String recipient) {
        super.send(message, recipient);
        System.out.println("Notificación en la aplicación para " + recipient + ": " + message);
    }
}
