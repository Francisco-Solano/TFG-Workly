package com.vedruna.TFG_Workly.dto;

import com.vedruna.TFG_Workly.models.Asignacion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AsignacionDTO {
    private Integer asignacionId;
    private Integer tareaId;    // ID de la tarea a la que se asigna el usuario
    private Integer usuarioId;  // ID del usuario que ha sido asignado


    public AsignacionDTO(Asignacion asignacion) {
        this.asignacionId = asignacion.getAsignacionId();
        this.tareaId = asignacion.getTarea().getTareaId();   // ID de la tarea
        this.usuarioId = asignacion.getUsuario().getUsuarioId();  // ID del usuario asignado
    }
}


