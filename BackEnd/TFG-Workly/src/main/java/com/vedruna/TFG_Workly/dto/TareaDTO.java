package com.vedruna.TFG_Workly.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.vedruna.TFG_Workly.models.Tarea;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class TareaDTO {
    private Integer tareaId;
    private String titulo;
    private String descripcion;
    private String estado;      // Ej: "Por hacer", "En progreso", "Hecho"
    private String prioridad;   // Ej: "Alta", "Media", "Baja"
    private Date fechaLimite;

    // Relaciones opcionales (según tu lógica de negocio)
    private List<SubtareaDTO> subtareas;
    private List<ComentarioDTO> comentarios;
    private List<EtiquetaDTO> etiquetas;

    public TareaDTO(Tarea tarea) {
        this.tareaId = tarea.getTareaId();
        this.titulo = tarea.getTitulo();
        this.descripcion = tarea.getDescripcion();
        this.estado = tarea.getEstado();
        this.prioridad = tarea.getPrioridad();
        this.fechaLimite = tarea.getFechaLimite();

        this.subtareas = tarea.getSubtareas() != null ?
                tarea.getSubtareas().stream().map(SubtareaDTO::new).collect(Collectors.toList()) : null;

        this.comentarios = tarea.getComentarios() != null ?
                tarea.getComentarios().stream().map(ComentarioDTO::new).collect(Collectors.toList()) : null;

        this.etiquetas = tarea.getEtiquetas() != null ?
                tarea.getEtiquetas().stream().map(EtiquetaDTO::new).collect(Collectors.toList()) : null;
    }
}
