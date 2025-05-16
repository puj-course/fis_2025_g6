package com.fis_2025_g6.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fis_2025_g6.service.SMSService;

@RestController
@RequestMapping("/sms")
public class SMSController {
    private final SMSService smsService;

    public SMSController(SMSService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/envio")
    public ResponseEntity<String> sendSMS(@RequestParam String number, @RequestParam String message) {
        smsService.sendSMS(number, message);
        return ResponseEntity.ok("SMS enviado a " + number);
    }
}
