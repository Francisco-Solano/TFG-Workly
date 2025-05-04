package com.vedruna.TFG_Workly.dto;

import com.vedruna.TFG_Workly.models.Archivo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ArchivoDTO {
    private Integer archivoId;
    private Integer tareaId;    // ID de la tarea asociada
    private String archivoUrl;   // URL del archivo

    // Constructor que recibe la entidad Archivo
    public ArchivoDTO(Archivo archivo) {
        this.archivoId = archivo.getArchivoId();
        this.tareaId = archivo.getTarea().getTareaId();    // ID de la tarea
        this.archivoUrl = archivo.getArchivoUrl();         // URL del archivo
    }
}
