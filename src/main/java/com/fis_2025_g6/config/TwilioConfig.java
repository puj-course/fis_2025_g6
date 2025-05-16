package com.fis_2025_g6.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;

@Configuration
public class TwilioConfig {
    @Value("${twilio.account.sid}")
    private String accountSID;

    @Value("${twilio.auth.token}")
    private String authToken;

    @PostConstruct
    public void init() {
        Twilio.init(accountSID, authToken);
    }
}
