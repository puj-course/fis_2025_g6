package com.fis_2025_g6.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "Mascota disponible para adopción")
public class PetDto {
    @NotBlank
    @Size(max = 30)
    @Schema(description = "Nombre de la mascota")
    private String name;

    @NotBlank
    @Schema(description = "Especie", example = "Perro")
    private String species;

    @Min(0)
    @Schema(description = "Edad en años")
    private Integer age;

    @NotBlank
    @Schema(description = "Sexo", example = "Hembra")
    private String sex;

    @NotBlank
    @Schema(description = "Descripción general")
    private String description;
}
