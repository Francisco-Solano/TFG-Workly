package com.vedruna.TFG_Workly.services;

import com.vedruna.TFG_Workly.dto.ArchivoDTO;
import com.vedruna.TFG_Workly.models.Tarea;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArchivoServiceI {
    // Subir un archivo a una tarea
    ArchivoDTO subirArchivo(Integer tareaId, MultipartFile archivo);

    // Obtener todos los archivos de una tarea
    List<ArchivoDTO> obtenerArchivosDeTarea(Tarea tareaId);

    // Eliminar un archivo
    void eliminarArchivo(Integer archivoId);
}
