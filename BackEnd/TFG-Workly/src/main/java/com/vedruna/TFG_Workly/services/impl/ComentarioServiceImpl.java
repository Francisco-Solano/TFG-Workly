package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.ComentarioDTO;
import com.vedruna.TFG_Workly.services.ComentarioServiceI;

import java.util.List;

public class ComentarioServiceImpl implements ComentarioServiceI {
    @Override
    public ComentarioDTO crearComentario(Integer tareaId, ComentarioDTO comentarioDTO) {
        return null;
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(Integer comentarioId) {
        return null;
    }

    @Override
    public List<ComentarioDTO> listarComentariosPorTarea(Integer tareaId) {
        return null;
    }

    @Override
    public ComentarioDTO actualizarComentario(Integer comentarioId, ComentarioDTO comentarioDTO) {
        return null;
    }

    @Override
    public void eliminarComentario(Integer comentarioId) {

    }
}
