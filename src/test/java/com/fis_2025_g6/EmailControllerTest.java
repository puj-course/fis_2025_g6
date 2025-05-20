package com.fis_2025_g6;

import com.fis_2025_g6.controller.EmailController;
import com.fis_2025_g6.entity.User;
import com.fis_2025_g6.service.EmailService;
import com.fis_2025_g6.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

public class EmailControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmailService emailService;

    @Mock
    private UserService userService;

    @InjectMocks
    private EmailController emailController;

    @BeforeEach
    void setUp() {
        org.mockito.MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(emailController).build();
    }


    @Test
    void testSendEmailToUser_Found() throws Exception {
        User recipient = new User();
        recipient.setId(1L);
        recipient.setUsername("destino");
        recipient.setEmail("destino@example.com");

        when(userService.findById(1L)).thenReturn(Optional.of(recipient));

        mockMvc.perform(post("/email/1/envio")
                .param("subject", "Asunto")
                .param("message", "Contenido del mensaje")
                .with(user("refugeUser").roles("REFUGE")))
            .andExpect(status().isOk())
            .andExpect(content().string("Correo enviado a destino"));

        verify(emailService).sendEmail("destino@example.com", "Asunto", "Contenido del mensaje");
    }

    @Test
    void testSendEmailToUser_NotFound() throws Exception {
        when(userService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/email/999/envio")
                .param("subject", "Asunto")
                .param("message", "Contenido del mensaje")
                .with(user("adminUser").roles("ADMIN")))
            .andExpect(status().isNotFound());

        verify(emailService, never()).sendEmail(any(), any(), any());
    }
}
