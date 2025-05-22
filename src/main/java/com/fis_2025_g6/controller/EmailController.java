package com.fis_2025_g6.controller;

import com.fis_2025_g6.auth.CustomUserDetails;
import com.fis_2025_g6.entity.User;
import com.fis_2025_g6.service.EmailService;
import com.fis_2025_g6.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;
    private final UserService userService;

    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @Operation(summary = "Enviarse correo", description = "Usuarios ADOPTANTE o REFUGIO")
    @PostMapping("/me")
    public ResponseEntity<String> sendMeEmail(
            @RequestParam String subject,
            @RequestParam String message,
            @AuthenticationPrincipal CustomUserDetails principal) {
        User destination = principal.getUser();
        emailService.sendEmail(destination.getEmail(), subject, message);
        return ResponseEntity.ok("Correo enviado a " + destination.getUsername());
    }

    @Operation(summary = "Enviar correo a un usuario", description = "Usuarios REFUGIO")
    @PreAuthorize("hasRole('REFUGE') or hasRole('ADMIN')")
    @PostMapping("/{id}/envio")
    public ResponseEntity<String> sendEmailToUser(
            @PathVariable Long id,
            @RequestParam String subject,
            @RequestParam String message) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User destination = user.get();
        emailService.sendEmail(destination.getEmail(), subject, message);
        return ResponseEntity.ok("Correo enviado a " + destination.getUsername());
    }
}
