package com.fis_2025_g6.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "Registro de usuario")
public class RegisterRequest {
    @NotBlank
    @Size(min = 4, max = 20, message = "El usuario debe tener entre 4 y 20 caracteres")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]*$", message = "El usuario debe utilizar solo caracteres alfanuméricos")
    @Schema(description = "Nombre de usuario", minLength = 4)
    private String username;

    @NotBlank
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Schema(description = "Contraseña", minLength = 8)
    private String password;

    @Email
    @NotBlank
    @Schema(description = "Correo electrónico")
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\+?[\\d\\s\\-()]{7,20}$", message = "El número de teléfono debe ser válido")
    @Schema(description = "Número de teléfono", example = "3146149812")
    private String phoneNumber;

    @NotBlank
    @Size(max = 60)
    @Schema(description = "Dirección particular", maxLength = 60)
    private String address;

    @NotBlank
    @Schema(description = "Tipo de usuario ('ADOPTANTE', 'REFUGIO', 'ADMIN')")
    private String type;
}
