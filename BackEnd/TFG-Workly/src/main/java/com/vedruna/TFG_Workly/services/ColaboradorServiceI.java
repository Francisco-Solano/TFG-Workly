package com.vedruna.TFG_Workly.services;

import com.vedruna.TFG_Workly.dto.ColaboradorDTO;

import java.util.List;

public interface ColaboradorServiceI {

    // Añadir un nuevo colaborador a un proyecto
    ColaboradorDTO añadirColaborador(Integer proyectoId, Integer usuarioId, String rol);

    // Obtener todos los colaboradores de un proyecto
    List<ColaboradorDTO> obtenerColaboradoresDeProyecto(Integer proyectoId);

    // Actualizar el rol de un colaborador en un proyecto
    ColaboradorDTO actualizarRolColaborador(Integer colaboradorId, String nuevoRol);

    // Eliminar un colaborador de un proyecto
    void eliminarColaborador(Integer colaboradorId);

    // Ver si un usuario específico es colaborador de un proyecto
    boolean esColaborador(Integer proyectoId, Integer usuarioId);
}
