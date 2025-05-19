package com.vedruna.TFG_Workly.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CrearTareaDTO {
    private String titulo;
    private String descripcion;
    private String estado;
    private String prioridad;
    private Date fechaLimite;
    private Integer tablaId;
    private List<Integer> etiquetaIds;
}


