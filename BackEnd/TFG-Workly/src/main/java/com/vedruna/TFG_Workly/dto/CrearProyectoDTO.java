package com.vedruna.TFG_Workly.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class CrearProyectoDTO {
    @NotBlank
    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private boolean visibilidad;

}