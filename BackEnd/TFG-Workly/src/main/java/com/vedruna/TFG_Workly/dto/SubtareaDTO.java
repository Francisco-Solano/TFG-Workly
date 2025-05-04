package com.vedruna.TFG_Workly.dto;

import com.vedruna.TFG_Workly.models.Subtarea;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SubtareaDTO {
    private Integer subtareaId;
    private String titulo;
    private String estado;
    private Integer tareaId; // ID de la tarea asociada


    public SubtareaDTO(Subtarea subtarea) {
        this.subtareaId = subtarea.getSubtareaId();
        this.titulo = subtarea.getTitulo();
        this.estado = subtarea.getEstado();
        this.tareaId = subtarea.getTarea().getTareaId(); // Solo el ID de la tarea
    }
}
