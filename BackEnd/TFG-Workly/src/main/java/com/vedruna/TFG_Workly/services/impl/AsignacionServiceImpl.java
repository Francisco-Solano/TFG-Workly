package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.AsignacionDTO;
import com.vedruna.TFG_Workly.services.AsignacionServiceI;

import java.util.List;

public class AsignacionServiceImpl implements AsignacionServiceI {
    @Override
    public AsignacionDTO asignarTareaAUsuario(Integer tareaId, Integer usuarioId) {
        return null;
    }

    @Override
    public List<AsignacionDTO> obtenerAsignacionesDeTarea(Integer tareaId) {
        return null;
    }

    @Override
    public List<AsignacionDTO> obtenerAsignacionesDeUsuario(Integer usuarioId) {
        return null;
    }

    @Override
    public void eliminarAsignacion(Integer asignacionId) {

    }
}
