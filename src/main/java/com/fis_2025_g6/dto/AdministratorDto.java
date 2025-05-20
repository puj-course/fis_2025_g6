package com.fis_2025_g6.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Usuario administrador")
public class AdministratorDto extends UserDto {}
