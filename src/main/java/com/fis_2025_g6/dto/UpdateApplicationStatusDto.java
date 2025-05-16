package com.fis_2025_g6.dto;

import com.fis_2025_g6.ApplicationStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "Actualización de estado de la solicitud")
public class UpdateApplicationStatusDto {
    @NotBlank
    @Schema(description = "Nuevo estado ('PENDING', 'APPROVED', 'REJECTED', 'CANCELED')")
    private ApplicationStatus status;
}
