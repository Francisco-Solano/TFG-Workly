package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.ProyectoDTO;
import com.vedruna.TFG_Workly.dto.TareaDTO;
import com.vedruna.TFG_Workly.dto.UsuarioDTO;
import com.vedruna.TFG_Workly.models.Usuario;
import com.vedruna.TFG_Workly.services.UsuarioServiceI;

import java.util.List;

public class UsuarioServiceImpl implements UsuarioServiceI {


    @Override
    public UsuarioDTO obtenerPerfil() {
        return null;
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorId(Integer id) {
        return null;
    }

    @Override
    public List<UsuarioDTO> obtenerUsuarios() {
        return null;
    }

    @Override
    public List<UsuarioDTO> buscarPorEmail(String email) {
        return null;
    }

    @Override
    public UsuarioDTO actualizarPerfil(UsuarioDTO usuarioDTO) {
        return null;
    }

    @Override
    public List<ProyectoDTO> obtenerProyectosDelUsuario() {
        return null;
    }

    @Override
    public List<TareaDTO> obtenerTareasAsignadas() {
        return null;
    }
}
