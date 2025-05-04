package com.vedruna.TFG_Workly.services;

import com.vedruna.TFG_Workly.dto.EtiquetaDTO;

import java.util.List;

public interface EtiquetaServiceI {
    // Crear una nueva etiqueta
    EtiquetaDTO crearEtiqueta(EtiquetaDTO etiquetaDTO);

    // Obtener una etiqueta por su ID
    EtiquetaDTO obtenerEtiquetaPorId(Integer etiquetaId);

    // Listar todas las etiquetas disponibles (puedes limitar por usuario o globales)
    List<EtiquetaDTO> listarEtiquetas();

    // Actualizar nombre o color de la etiqueta
    EtiquetaDTO actualizarEtiqueta(Integer etiquetaId, EtiquetaDTO etiquetaDTO);

    // Eliminar una etiqueta
    void eliminarEtiqueta(Integer etiquetaId);

    // Asignar una etiqueta a una tarea
    void asignarEtiquetaATarea(Integer etiquetaId, Integer tareaId);

    // Quitar una etiqueta de una tarea
    void quitarEtiquetaDeTarea(Integer etiquetaId, Integer tareaId);
}
