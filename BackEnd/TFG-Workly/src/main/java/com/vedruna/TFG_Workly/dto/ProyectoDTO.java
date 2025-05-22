package com.vedruna.TFG_Workly.dto;

import com.vedruna.TFG_Workly.models.Proyecto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class ProyectoDTO {
    private Integer proyectoId;
    private String nombre;
    private boolean visibilidad;
    private Integer adminId;
    private List<Integer> tablaIds; // Lista de IDs de las tablas asociadas al proyecto


    public ProyectoDTO(Proyecto proyecto) {
        this.proyectoId = proyecto.getProyectoId();
        this.nombre = proyecto.getNombre();
        this.visibilidad = proyecto.isVisibilidad();
        this.adminId = proyecto.getUsuario().getUsuarioId(); // Solo el ID del administrador

        this.tablaIds = proyecto.getTablas().stream()
                .map(tabla -> tabla.getTablaId()) // Obtenemos los IDs de las tablas asociadas
                .collect(Collectors.toList());


    }

}




