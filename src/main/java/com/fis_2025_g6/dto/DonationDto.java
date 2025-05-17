package com.fis_2025_g6.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter
@Schema(description = "Donación para un refugio")
public class DonationDto {
    @NonNull
    @Min(1)
    @Schema(description = "Monto de la donación")
    private Double amount;

    @NotNull
    @Schema(description = "Nombre de usuario del refugio de destino")
    private String refugeUsername;
}
