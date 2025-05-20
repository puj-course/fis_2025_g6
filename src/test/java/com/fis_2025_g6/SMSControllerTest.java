package com.fis_2025_g6;

import com.fis_2025_g6.auth.CustomUserDetails;
import com.fis_2025_g6.entity.User;
import com.fis_2025_g6.service.NotificationService;
import com.fis_2025_g6.service.UserService;
import com.fis_2025_g6.controller.SMSController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SMSControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private UserService userService;
    
    @Test
public void testSendMeSMS() throws Exception {
    // Crear un usuario falso
    User mockUser = new User();
    mockUser.setPhoneNumber("123456789");
    mockUser.setUsername("usuario123");

    // Crear CustomUserDetails a partir del usuario
    CustomUserDetails userDetails = new CustomUserDetails(mockUser);

    // Simular autenticación en el contexto de seguridad
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
    );
    SecurityContextHolder.setContext(context);

    // Ejecutar la solicitud y verificar respuesta
    mockMvc.perform(post("/sms/me")
            .param("number", "123456789")
            .param("message", "Hola mundo"))
        .andExpect(status().isOk())
        .andExpect(content().string("SMS enviado a 123456789"));

    // Verificar que se haya llamado al método send con los argumentos correctos
    Mockito.verify(notificationService).send("Hola mundo", "123456789");
}


    @Test
    @WithMockUser(username = "refugio", roles = {"REFUGE"})
    void testSendSMS() throws Exception {
        mockMvc.perform(post("/sms/envio")
                .param("number", "987654321")
                .param("message", "Mensaje de prueba"))
            .andExpect(status().isOk())
            .andExpect(content().string("SMS enviado a 987654321"));

        Mockito.verify(notificationService).send("Mensaje de prueba", "987654321");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSendSmsToUserFound() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("userDestino");
        user.setPhoneNumber("111222333");

        Mockito.when(userService.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(post("/sms/1/envio")
                .param("message", "Hola usuario"))
            .andExpect(status().isOk())
            .andExpect(content().string("SMS enviado a userDestino"));

        Mockito.verify(notificationService).send("Hola usuario", "111222333");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSendSmsToUserNotFound() throws Exception {
        Mockito.when(userService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/sms/99/envio")
                .param("message", "Hola desconocido"))
            .andExpect(status().isNotFound());
    }
}

