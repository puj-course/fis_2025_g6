package com.fis_2025_g6.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "Solicitud de adopción")
public class ApplicationDto {
    @NotNull
    @Schema(description = "ID de la mascota a adoptar")
    private Long petId;
}
