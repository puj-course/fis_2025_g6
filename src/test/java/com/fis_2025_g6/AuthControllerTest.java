package com.fis_2025_g6;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fis_2025_g6.auth.JwtUtil;
import com.fis_2025_g6.controller.AuthController;
import com.fis_2025_g6.dto.AuthRequest;
import com.fis_2025_g6.dto.RegisterRequest;
import com.fis_2025_g6.entity.User;
import com.fis_2025_g6.factory.AdministratorFactory;
import com.fis_2025_g6.factory.AdoptantFactory;
import com.fis_2025_g6.factory.RefugeFactory;
import com.fis_2025_g6.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AdoptantFactory adoptantFactory;

    @Mock
    private RefugeFactory refugeFactory;

    @Mock
    private AdministratorFactory administratorFactory;

    @InjectMocks
    private AuthController authController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testLoginSuccess() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setUsername("testuser");
        request.setPassword("testpass");

        Authentication auth = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("mock-jwt-token");

        mockMvc.perform(post("/auth/iniciosesion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-jwt-token"));
    }

    @Test
    void testRegisterSuccess_Adoptant() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("nuevoUser");
        request.setPassword("contrasenaSegura");
        request.setEmail("nuevo@example.com");
        request.setPhoneNumber("1234567890");
        request.setAddress("Calle 123");
        request.setType("ADOPTANTE");

        when(userRepository.findByEmail("nuevo@example.com")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("nuevoUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("contrasenaSegura")).thenReturn("hashedPassword");

        User newUser = new User();
        when(adoptantFactory.create(any(), any(), any(), any(), any())).thenReturn(newUser);

        mockMvc.perform(post("/auth/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario registrado con éxito"));

        verify(userRepository).save(newUser);
    }

    @Test
    void testRegisterError_EmailExists() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("nuevoUser");
        request.setPassword("contrasenaSegura");
        request.setEmail("existente@example.com");
        request.setPhoneNumber("1234567890");
        request.setAddress("Calle 123");
        request.setType("ADOPTANTE");

        when(userRepository.findByEmail("existente@example.com")).thenReturn(Optional.of(new User()));

        mockMvc.perform(post("/auth/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El correo ya está registrado"));
    }

    @Test
    void testRegisterError_UsernameExists() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existenteUser");
        request.setPassword("contrasenaSegura");
        request.setEmail("nuevo@example.com");
        request.setPhoneNumber("1234567890");
        request.setAddress("Calle 123");
        request.setType("REFUGIO");

        when(userRepository.findByEmail("nuevo@example.com")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("existenteUser")).thenReturn(Optional.of(new User()));

        mockMvc.perform(post("/auth/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El usuario ya está registrado"));
    }

    @Test
    void testRegisterError_InvalidType() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("user");
        request.setPassword("contrasenaSegura");
        request.setEmail("user@example.com");
        request.setPhoneNumber("1234567890");
        request.setAddress("Calle 123");
        request.setType("INVÁLIDO");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        mockMvc.perform(post("/auth/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Tipo de usuario inválido"));
    }
}
