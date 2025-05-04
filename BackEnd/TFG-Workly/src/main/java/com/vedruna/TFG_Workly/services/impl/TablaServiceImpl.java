package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.TablaDTO;
import com.vedruna.TFG_Workly.services.TablaServiceI;

import java.util.List;

public class TablaServiceImpl implements TablaServiceI {
    @Override
    public TablaDTO crearTabla(Integer proyectoId, TablaDTO tablaDTO) {
        return null;
    }

    @Override
    public TablaDTO obtenerTablaPorId(Integer tablaId) {
        return null;
    }

    @Override
    public List<TablaDTO> listarTablasPorProyecto(Integer proyectoId) {
        return null;
    }

    @Override
    public TablaDTO actualizarTabla(Integer tablaId, TablaDTO tablaDTO) {
        return null;
    }

    @Override
    public void cambiarPosicion(Integer tablaId, int nuevaPosicion) {

    }

    @Override
    public void eliminarTabla(Integer tablaId) {

    }
}
