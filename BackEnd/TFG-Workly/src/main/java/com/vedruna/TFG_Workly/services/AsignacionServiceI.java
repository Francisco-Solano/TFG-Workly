package com.vedruna.TFG_Workly.services;

import com.vedruna.TFG_Workly.dto.AsignacionDTO;

import java.util.List;

public interface AsignacionServiceI {
    // Asignar una tarea a un usuario
    AsignacionDTO asignarTareaAUsuario(Integer tareaId, Integer usuarioId);

    // Obtener todas las asignaciones de una tarea
    List<AsignacionDTO> obtenerAsignacionesDeTarea(Integer tareaId);

    // Obtener todas las tareas asignadas a un usuario
    List<AsignacionDTO> obtenerAsignacionesDeUsuario(Integer usuarioId);

    // Eliminar una asignación (desasignar usuario de la tarea)
    void eliminarAsignacion(Integer asignacionId);
}


