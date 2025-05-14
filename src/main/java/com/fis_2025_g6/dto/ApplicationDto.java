package com.fis_2025_g6.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApplicationDto {
    @NotNull
    private Long petId;
}
