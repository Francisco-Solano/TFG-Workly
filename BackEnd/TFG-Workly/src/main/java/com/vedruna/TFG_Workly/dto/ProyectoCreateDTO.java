package com.vedruna.TFG_Workly.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class ProyectoCreateDTO {
    @NotBlank
    private String nombre;
    private boolean visibilidad;

}