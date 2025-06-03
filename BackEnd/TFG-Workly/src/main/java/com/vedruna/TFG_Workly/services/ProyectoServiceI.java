package com.vedruna.TFG_Workly.services;

import com.vedruna.TFG_Workly.dto.CrearProyectoDTO;
import com.vedruna.TFG_Workly.dto.ProyectoDTO;
import com.vedruna.TFG_Workly.dto.UsuarioDTO;
import com.vedruna.TFG_Workly.models.Usuario;

import java.util.List;

public interface ProyectoServiceI {
    // Crear nuevo proyecto (solo el usuario autenticado como admin)
    ProyectoDTO crearProyecto(CrearProyectoDTO proyectoDTO, Usuario usuario);

    // Obtener proyecto por ID (si el usuario tiene acceso)
    ProyectoDTO obtenerProyectoPorId(Integer id);

    List<ProyectoDTO> obtenerProyectosComoColaborador(Integer usuarioId);

    // Actualizar proyecto (nombre, visibilidad, etc.)
    ProyectoDTO actualizarProyecto(Integer id, ProyectoDTO proyectoDTO);

    // Eliminar proyecto (solo si el usuario es admin del mismo)
    void eliminarProyecto(Integer id);

    // Listar todos los proyectos del usuario autenticado (admin o colaborador)
    List<ProyectoDTO> listarProyectosUsuario();

    // Añadir un colaborador al proyecto (solo admin)
    void añadirColaborador(Integer proyectoId, Integer usuarioId, String rol);

    // Eliminar colaborador de un proyecto (solo admin)
    void eliminarColaborador(Integer proyectoId, Integer usuarioId);

    // Listar colaboradores de un proyecto (admin y miembros)
    List<UsuarioDTO> listarColaboradores(Integer proyectoId);





}


