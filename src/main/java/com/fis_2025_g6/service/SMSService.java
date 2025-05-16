package com.fis_2025_g6.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SMSService {
    @Value("${twilio.phone.number}")
    private String from;

    public void sendSMS(String to, String body) {
        to = to.trim();
        Message.creator(new PhoneNumber(to), new PhoneNumber(from), body).create();
    }
}