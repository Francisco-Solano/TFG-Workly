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
    private String estado;
    private String prioridad;
    private Date fechaLimite;
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
        this.subtareas = tarea.getSubtareas().stream()
                .map(SubtareaDTO::new) // Mapear a un DTO simplificado
                .collect(Collectors.toList());
        this.comentarios = tarea.getComentarios().stream()
                .map(ComentarioDTO::new) // Mapear los comentarios
                .collect(Collectors.toList());
        this.etiquetas = tarea.getEtiquetas().stream()
                .map(EtiquetaDTO::new) // Mapear las etiquetas
                .collect(Collectors.toList());
    }
}

