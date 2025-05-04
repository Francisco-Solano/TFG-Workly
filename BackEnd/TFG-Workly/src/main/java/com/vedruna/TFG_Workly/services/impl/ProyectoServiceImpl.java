package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.ProyectoDTO;
import com.vedruna.TFG_Workly.dto.UsuarioDTO;
import com.vedruna.TFG_Workly.models.Proyecto;
import com.vedruna.TFG_Workly.models.Usuario;
import com.vedruna.TFG_Workly.repositories.IProyectoRepository;
import com.vedruna.TFG_Workly.repositories.IUsuarioRepository;
import com.vedruna.TFG_Workly.services.ProyectoServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoServiceImpl implements ProyectoServiceI {

    private final IProyectoRepository proyectoRepository;
    private final IUsuarioRepository usuarioRepository;

    @Autowired
    public ProyectoServiceImpl(IProyectoRepository proyectoRepository, IUsuarioRepository usuarioRepository) {
        this.proyectoRepository = proyectoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public ProyectoDTO crearProyecto(ProyectoDTO proyectoDTO) {
        return null;
    }

    @Override
    public ProyectoDTO obtenerProyectoPorId(Integer id) {
        return null;
    }

    @Override
    public ProyectoDTO actualizarProyecto(Integer id, ProyectoDTO proyectoDTO) {
        return null;
    }

    @Override
    public void eliminarProyecto(Integer id) {

    }

    @Override
    public List<ProyectoDTO> listarProyectosUsuario() {
        return null;
    }

    @Override
    public void añadirColaborador(Integer proyectoId, Integer usuarioId, String rol) {

    }

    @Override
    public void eliminarColaborador(Integer proyectoId, Integer usuarioId) {

    }

    @Override
    public List<UsuarioDTO> listarColaboradores(Integer proyectoId) {
        return null;
    }

    @Override
    public ProyectoDTO cambiarVisibilidad(Integer proyectoId, boolean nuevaVisibilidad) {
        return null;
    }

    @Override
    public List<ProyectoDTO> buscarProyectosPublicos(String nombre) {
        return null;
    }
}
