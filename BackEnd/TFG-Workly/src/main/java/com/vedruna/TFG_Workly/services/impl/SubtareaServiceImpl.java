package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.SubtareaDTO;
import com.vedruna.TFG_Workly.services.SubtareaServiceI;

import java.util.List;

public class SubtareaServiceImpl implements SubtareaServiceI {
    @Override
    public SubtareaDTO crearSubtarea(Integer tareaId, SubtareaDTO subtareaDTO) {
        return null;
    }

    @Override
    public SubtareaDTO obtenerSubtareaPorId(Integer subtareaId) {
        return null;
    }

    @Override
    public SubtareaDTO actualizarSubtarea(Integer subtareaId, SubtareaDTO subtareaDTO) {
        return null;
    }

    @Override
    public void cambiarEstado(Integer subtareaId, String nuevoEstado) {

    }

    @Override
    public void eliminarSubtarea(Integer subtareaId) {

    }

    @Override
    public List<SubtareaDTO> listarSubtareasPorTarea(Integer tareaId) {
        return null;
    }
}
