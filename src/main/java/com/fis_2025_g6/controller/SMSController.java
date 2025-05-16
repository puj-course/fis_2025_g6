package com.fis_2025_g6.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fis_2025_g6.entity.User;
import com.fis_2025_g6.service.SMSService;
import com.fis_2025_g6.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/sms")
public class SMSController {
    private final SMSService smsService;
    private final UserService userService;

    public SMSController(SMSService smsService, UserService userService) {
        this.smsService = smsService;
        this.userService = userService;
    }

    @Operation(summary = "Enviar un SMS a un número de teléfono", description = "Usuarios REFUGIO")
    @PreAuthorize("hasRole('REFUGE') or hasRole('ADMIN')")
    @PostMapping("/envio")
    public ResponseEntity<String> sendSMS(@RequestParam String number, @RequestParam String message) {
        smsService.sendSMS(number, message);
        return ResponseEntity.ok("SMS enviado a " + number);
    }

    @Operation(summary = "Enviar un SMS a un usuario", description = "Usuarios REFUGIO")
    @PreAuthorize("hasRole('REFUGE') or hasRole('ADMIN')")
    @PostMapping("/{id}/envio")
    public ResponseEntity<String> sendSmsToUser(@PathVariable Long id, @RequestParam String message) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User destination = user.get();
        smsService.sendSMS(destination.getPhoneNumber(), message);
        return ResponseEntity.ok("SMS enviado a" + destination.getUsername());
    }
}
