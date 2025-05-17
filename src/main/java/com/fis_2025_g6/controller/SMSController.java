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
import com.fis_2025_g6.service.NotificationService;
import com.fis_2025_g6.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/sms")
public class SMSController {
    private final NotificationService notificationService;
    private final UserService userService;

    public SMSController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @Operation(summary = "Enviar un SMS a un número de teléfono", description = "Usuarios REFUGIO")
    @PreAuthorize("hasRole('REFUGE') or hasRole('ADMIN')")
    @PostMapping("/envio")
    public ResponseEntity<String> sendSMS(@RequestParam String number, @RequestParam String message) {
        notificationService.send(message, number);
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
        notificationService.send(message, destination.getPhoneNumber());
        return ResponseEntity.ok("SMS enviado a " + destination.getUsername());
    }
}
