package com.fis_2025_g6.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AdoptantDto extends UserDto {
    private String adoptantName;
}
