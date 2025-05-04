package com.vedruna.TFG_Workly.dto;

import com.vedruna.TFG_Workly.models.Comentario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class ComentarioDTO {
    private Integer comentarioId;
    private Integer usuarioId;
    private Integer tareaId;
    private String contenido;
    private Date fechaCreacion;

    public ComentarioDTO(Comentario comentario) {
        this.comentarioId = comentario.getComentarioId();
        this.usuarioId = comentario.getUsuario().getUsuarioId();
        this.tareaId = comentario.getTarea().getTareaId();
        this.contenido = comentario.getContenido();
        this.fechaCreacion = comentario.getFechaCreacion();
    }
}
