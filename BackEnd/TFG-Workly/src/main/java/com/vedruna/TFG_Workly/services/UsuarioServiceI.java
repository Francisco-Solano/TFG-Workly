package com.vedruna.TFG_Workly.services;

import com.vedruna.TFG_Workly.dto.ProyectoDTO;
import com.vedruna.TFG_Workly.dto.TareaDTO;
import com.vedruna.TFG_Workly.dto.UsuarioDTO;
import com.vedruna.TFG_Workly.models.Usuario;

import java.util.List;

public interface UsuarioServiceI {
    // Obtener perfil del usuario autenticado
    UsuarioDTO obtenerPerfil();

    // Obtener usuario por ID (para admin o por motivos específicos)
    UsuarioDTO obtenerUsuarioPorId(Integer id);

    //Para obtener todos los usuarios
    List<UsuarioDTO> obtenerUsuarios();

    // Buscar usuario por nombre o email (útil para invitar a colaborar, como en Trello)
    List<UsuarioDTO> buscarPorEmail(String email);

    // Actualizar perfil del usuario autenticado
    UsuarioDTO actualizarPerfil(UsuarioDTO usuarioDTO);

    // Listar todos los proyectos en los que el usuario participa (propios + colaboraciones)
    List<ProyectoDTO> obtenerProyectosDelUsuario();

    // Listar todas las tareas asignadas a este usuario
    List<TareaDTO> obtenerTareasAsignadas();

}
