package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.EtiquetaDTO;
import com.vedruna.TFG_Workly.services.EtiquetaServiceI;

import java.util.List;

public class EtiquetaServiceImpl implements EtiquetaServiceI {
    @Override
    public EtiquetaDTO crearEtiqueta(EtiquetaDTO etiquetaDTO) {
        return null;
    }

    @Override
    public EtiquetaDTO obtenerEtiquetaPorId(Integer etiquetaId) {
        return null;
    }

    @Override
    public List<EtiquetaDTO> listarEtiquetas() {
        return null;
    }

    @Override
    public EtiquetaDTO actualizarEtiqueta(Integer etiquetaId, EtiquetaDTO etiquetaDTO) {
        return null;
    }

    @Override
    public void eliminarEtiqueta(Integer etiquetaId) {

    }

    @Override
    public void asignarEtiquetaATarea(Integer etiquetaId, Integer tareaId) {

    }

    @Override
    public void quitarEtiquetaDeTarea(Integer etiquetaId, Integer tareaId) {

    }
}
