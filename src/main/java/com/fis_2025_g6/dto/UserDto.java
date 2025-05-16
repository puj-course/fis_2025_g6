package com.fis_2025_g6.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class UserDto {
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
}
