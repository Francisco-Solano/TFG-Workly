package com.vedruna.TFG_Workly.services;

import com.vedruna.TFG_Workly.dto.ComentarioDTO;

import java.util.List;

public interface ComentarioServiceI {
    // Crear un comentario dentro de una tarea
    ComentarioDTO crearComentario(Integer tareaId, ComentarioDTO comentarioDTO);

    // Obtener un comentario por su ID (por si necesitas editarlo o ver detalles)
    ComentarioDTO obtenerComentarioPorId(Integer comentarioId);

    // Listar todos los comentarios de una tarea
    List<ComentarioDTO> listarComentariosPorTarea(Integer tareaId);

    // Actualizar un comentario (solo si el autor es quien edita)
    ComentarioDTO actualizarComentario(Integer comentarioId, ComentarioDTO comentarioDTO);

    // Eliminar un comentario (puede hacerlo el autor o el admin del proyecto)
    void eliminarComentario(Integer comentarioId);
}


