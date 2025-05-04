package com.vedruna.TFG_Workly.dto;

import com.vedruna.TFG_Workly.models.Colaborador;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ColaboradorDTO {
    private Integer colaboradorId;
    private Integer proyectoId;
    private Integer usuarioId;
    private String rol;


    public ColaboradorDTO(Colaborador colaborador) {
        this.colaboradorId = colaborador.getColaboradorId();
        this.proyectoId = colaborador.getProyecto().getProyectoId();
        this.usuarioId = colaborador.getUsuario().getUsuarioId();
        this.rol = colaborador.getRol();
    }
}
