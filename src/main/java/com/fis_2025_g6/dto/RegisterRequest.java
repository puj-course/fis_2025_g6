package com.fis_2025_g6.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank
    @Size(min = 4, max = 20, message = "El usuario debe tener entre 4 y 20 caracteres")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]*$", message = "El usuario debe utilizar solo caracteres alfanuméricos")
    private String username;

    @NotBlank
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\+?[\\d\\s\\-()]{7,20}$", message = "El número de teléfono debe ser válido")
    private String phoneNumber;

    @NotBlank
    @Size(max = 60)
    private String address;

    private String type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String val) {
        username = val;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String val) {
        password = val;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String val) {
        email = val;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String val) {
        phoneNumber = val;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String val) {
        address = val;
    }

    public String getType() {
        return type;
    }

    public void setType(String val) {
        type = val;
    }
}
