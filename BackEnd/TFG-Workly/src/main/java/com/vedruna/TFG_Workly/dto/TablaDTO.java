package com.vedruna.TFG_Workly.dto;

import com.vedruna.TFG_Workly.models.Tabla;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class TablaDTO {
    private Integer tablaId;
    private String nombre;
    private int posicion;
    private String color;
    private Integer proyectoId; // Solo el ID del proyecto asociado
    private List<TareaDTO> tareas; // Mapeamos las tareas asociadas


    public TablaDTO(Tabla tabla) {
        this.tablaId = tabla.getTablaId();
        this.nombre = tabla.getNombre();
        this.posicion = tabla.getPosicion();
        this.color = tabla.getColor();
        this.proyectoId = tabla.getProyecto().getProyectoId();

        this.tareas = tabla.getTareas().stream()
                .map(TareaDTO::new) // Mapeamos las tareas a su correspondiente DTO
                .collect(Collectors.toList());


    }
}


