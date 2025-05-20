package com.fis_2025_g6.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
@Schema(description = "Usuario")
public class UserDto {
    @NotBlank
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]*$")
    @Schema(description = "Nombre de usuario", minLength = 4)
    private String username;

    @NotBlank
    @Size(min = 8)
    @Schema(description = "Contraseña", minLength = 8)
    private String password;

    @Email
    @NotBlank
    @Schema(description = "Correo electrónico")
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\+?[\\d\\s\\-()]{7,20}$")
    @Schema(description = "Número de teléfono")
    private String phoneNumber;

    @NotBlank
    @Size(max = 60)
    @Schema(description = "Dirección particular", maxLength = 60)
    private String address;
}
