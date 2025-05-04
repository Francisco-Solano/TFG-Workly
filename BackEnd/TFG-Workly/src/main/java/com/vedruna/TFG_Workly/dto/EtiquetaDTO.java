package com.vedruna.TFG_Workly.dto;

import com.vedruna.TFG_Workly.models.Etiqueta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class EtiquetaDTO {
    private Integer etiquetaId;
    private String nombre;
    private String color;
    private List<Integer> tareaIds; // Lista de IDs de las tareas asociadas a la etiqueta

    public EtiquetaDTO(Etiqueta etiqueta) {
        this.etiquetaId = etiqueta.getEtiquetaId();
        this.nombre = etiqueta.getNombre();
        this.color = etiqueta.getColor();
        this.tareaIds = etiqueta.getTareas().stream()
                .map(tarea -> tarea.getTareaId()) // Obtenemos los IDs de las tareas asociadas
                .collect(Collectors.toList());
    }
}
