package com.fis_2025_g6.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "Inicio de sesión de usuario")
public class AuthRequest {
    @NotBlank
    @Schema(description = "Nombre de usuario")
    private String username;

    @NotBlank
    @Schema(description = "Contraseña")
    private String password;
}
