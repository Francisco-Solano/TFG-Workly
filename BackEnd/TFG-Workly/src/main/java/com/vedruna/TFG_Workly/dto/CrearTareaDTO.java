package com.vedruna.TFG_Workly.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CrearTareaDTO {
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    private String descripcion;
    private String estado;
    private String prioridad;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "fecha_limite")
    private Date fechaLimite;
    @NotNull(message = "El ID de la tabla es obligatorio")
    private Integer tablaId;
   // private List<Integer> etiquetaIds;
    //TODO  poner a que tabla pertenece la tarea
}


