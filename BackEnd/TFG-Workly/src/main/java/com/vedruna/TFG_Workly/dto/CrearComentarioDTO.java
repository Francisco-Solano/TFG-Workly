package com.vedruna.TFG_Workly.dto;

import lombok.Getter;
import lombok.Setter;

public class CrearComentarioDTO {
    @Getter
    @Setter
    public String contenido;

    @Getter
    @Setter
    public Integer usuarioId;
}
