package com.fis_2025_g6.notification;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("smsNotifier")
public class SMSNotifier implements Notifier {
    @Value("${twilio.phone.number}")
    private String from;

    @Override
    public void send(String message, String recipient) {
        Message.creator(new PhoneNumber(recipient.trim()), new PhoneNumber(from), message).create();
        System.out.println("Enviado SMS a " + recipient + ": " + message);
    }
}
