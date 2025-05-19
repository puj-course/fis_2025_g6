package com.fis_2025_g6.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = false)
@Getter @Setter
@Schema(description = "Usuario adoptante")
public class AdoptantDto extends UserDto {
    @NotBlank
    @Size(max = 30)
    @Schema(description = "Nombre del adoptante")
    private String adoptantName;
}
