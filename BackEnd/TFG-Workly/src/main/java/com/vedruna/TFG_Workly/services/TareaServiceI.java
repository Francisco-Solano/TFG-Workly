package com.vedruna.TFG_Workly.services;

import com.vedruna.TFG_Workly.dto.CrearTareaDTO;
import com.vedruna.TFG_Workly.dto.TareaDTO;

import java.util.Date;
import java.util.List;

public interface TareaServiceI {
    // Crear una tarea en una tabla específica
    TareaDTO crearTarea(CrearTareaDTO crearTareaDTO);

    // Obtener tarea por ID
    TareaDTO obtenerTareaPorId(Integer tareaId);

    // Actualizar los datos de la tarea (título, descripción, etc.)
    TareaDTO actualizarTarea(Integer tareaId, CrearTareaDTO tareaDTO);

    // Eliminar tarea
    void eliminarTarea(Integer tareaId);

    // Mover tarea a otra tabla (drag and drop)
    void moverTarea(Integer tareaId, Integer nuevaTablaId);

    // Cambiar estado de la tarea (por ejemplo: "Pendiente", "En progreso", "Hecho")
    void cambiarEstado(Integer tareaId, String nuevoEstado);

    // Cambiar prioridad
    void cambiarPrioridad(Integer tareaId, String nuevaPrioridad);

    // Añadir una etiqueta a una tarea
    void añadirEtiqueta(Integer tareaId, Integer etiquetaId);

    // Quitar una etiqueta de una tarea
    void quitarEtiqueta(Integer tareaId, Integer etiquetaId);

    // Asignar usuario a la tarea
    void asignarUsuario(Integer tareaId, Integer usuarioId);

    // Desasignar usuario de la tarea
    void desasignarUsuario(Integer tareaId, Integer usuarioId);

    // Establecer o actualizar fecha límite
    void actualizarFechaLimite(Integer tareaId, Date nuevaFechaLimite);

    // Listar tareas de una tabla específica
    List<TareaDTO> listarTareasPorTabla(Integer tablaId);

    // Listar tareas asignadas al usuario autenticado
    List<TareaDTO> listarTareasAsignadasAlUsuario();
}
