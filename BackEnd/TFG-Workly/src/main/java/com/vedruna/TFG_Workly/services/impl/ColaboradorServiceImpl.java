package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.ColaboradorDTO;
import com.vedruna.TFG_Workly.services.ColaboradorServiceI;

import java.util.List;

public class ColaboradorServiceImpl implements ColaboradorServiceI {
    @Override
    public ColaboradorDTO añadirColaborador(Integer proyectoId, Integer usuarioId, String rol) {
        return null;
    }

    @Override
    public List<ColaboradorDTO> obtenerColaboradoresDeProyecto(Integer proyectoId) {
        return null;
    }

    @Override
    public ColaboradorDTO actualizarRolColaborador(Integer colaboradorId, String nuevoRol) {
        return null;
    }

    @Override
    public void eliminarColaborador(Integer colaboradorId) {

    }

    @Override
    public boolean esColaborador(Integer proyectoId, Integer usuarioId) {
        return false;
    }
}
