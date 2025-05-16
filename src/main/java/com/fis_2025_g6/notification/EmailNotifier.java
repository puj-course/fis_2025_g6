package com.fis_2025_g6.notification;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("emailNotifier")
public class EmailNotifier extends NotifierDecorator {
    public EmailNotifier(@Qualifier("smsNotifier") Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message, String recipient) {
        super.send(message, recipient);
        System.out.println("Enviado correo a " + recipient + ": " + message);
    }
}
