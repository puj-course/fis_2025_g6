package com.fis_2025_g6;

import static org.mockito.Mockito.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.fis_2025_g6.auth.JwtAuthFilter;
import com.fis_2025_g6.auth.JwtUtil;

public class JwtAuthFilterTest {

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldAuthenticateWhenTokenIsValid() throws Exception {
        String token = "valid.jwt.token";
        String username = "testuser";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.validateToken(token, userDetails)).thenReturn(true);
        when(userDetails.getAuthorities()).thenReturn(null);

        jwtAuthFilter.doFilter(request, response, filterChain);

        // Verifica que el contexto de seguridad fue actualizado
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication instanceof UsernamePasswordAuthenticationToken;
        assert authentication.getPrincipal().equals(userDetails);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticateWhenHeaderIsMissing() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthFilter.doFilter(request, response, filterChain);

        // No debe haber autenticación
        assert SecurityContextHolder.getContext().getAuthentication() == null;

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticateWhenTokenIsInvalid() throws Exception {
        String token = "invalid.jwt.token";
        String username = "testuser";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.validateToken(token, userDetails)).thenReturn(false);

        jwtAuthFilter.doFilter(request, response, filterChain);

        assert SecurityContextHolder.getContext().getAuthentication() == null;

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticateWhenContextAlreadyHasAuthentication() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken("existing", null, null)
        );

        when(request.getHeader("Authorization")).thenReturn("Bearer sometoken");
        when(jwtUtil.extractUsername("sometoken")).thenReturn("user");

        jwtAuthFilter.doFilter(request, response, filterChain);

        verifyNoInteractions(userDetailsService);
        verify(filterChain).doFilter(request, response);
    }
}
