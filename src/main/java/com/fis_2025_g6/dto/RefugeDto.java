package com.fis_2025_g6.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Usuario refugio")
public class RefugeDto extends UserDto {
    @NotBlank
    @Size(max = 30)
    @Schema(description = "Nombre del refugio")
    private String refugeName;

    @NotBlank
    @Size(max = 100)
    @Schema(description = "Descripción del refugio")
    private String description;
}
