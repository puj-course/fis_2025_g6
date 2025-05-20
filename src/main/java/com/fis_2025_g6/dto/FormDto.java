package com.fis_2025_g6.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "Formulario de la solicitud de adopción")
public class FormDto {
    @NotNull
    @Schema(description = "ID de la solicitud")
    private Long applicationId;

    @NotNull
    @Schema(description = "¿Tiene otras masctoas?")
    private boolean hasPets;

    @NotNull
    @Schema(description = "¿Tiene suficiente espacio?")
    private boolean hasRoom;

    @NotBlank
    @Schema(description = "Tipo de vivienda")
    private String housingType;

    @Min(0)
    @Schema(description = "Horas fuera de casa")
    private Integer hoursAwayFromHome;

    @NotNull
    @Schema(description = "Compromiso de vacunación")
    private boolean vaccinationCommitment;

    @Schema(description = "Experiencia previa con mascotas")
    private String previousExperience;
}
