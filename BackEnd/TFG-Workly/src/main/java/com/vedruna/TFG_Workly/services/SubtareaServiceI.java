package com.vedruna.TFG_Workly.services;

import com.vedruna.TFG_Workly.dto.SubtareaDTO;

import java.util.List;

public interface SubtareaServiceI {
    // Crear una subtarea para una tarea
    SubtareaDTO crearSubtarea(Integer tareaId, SubtareaDTO subtareaDTO);

    // Obtener una subtarea por ID
    SubtareaDTO obtenerSubtareaPorId(Integer subtareaId);

    // Actualizar título o estado
    SubtareaDTO actualizarSubtarea(Integer subtareaId, SubtareaDTO subtareaDTO);

    // Cambiar estado (ej: marcada como completada o no)
    void cambiarEstado(Integer subtareaId, String nuevoEstado);

    // Eliminar subtarea
    void eliminarSubtarea(Integer subtareaId);

    // Listar subtareas de una tarea
    List<SubtareaDTO> listarSubtareasPorTarea(Integer tareaId);
}
