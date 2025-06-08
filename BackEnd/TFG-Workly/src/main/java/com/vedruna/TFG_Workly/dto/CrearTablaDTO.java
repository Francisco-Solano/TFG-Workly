package com.vedruna.TFG_Workly.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class CrearTablaDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Getter
    @Setter

    private String nombre;
    @Getter
    @Setter

    private Integer IdProyecto;

}
