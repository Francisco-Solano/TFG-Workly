package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.TareaDTO;
import com.vedruna.TFG_Workly.services.TareaServiceI;

import java.util.Date;
import java.util.List;

public class TareaServiceImpl implements TareaServiceI {


    @Override
    public TareaDTO crearTarea(Integer tablaId, TareaDTO tareaDTO) {
        return null;
    }

    @Override
    public TareaDTO obtenerTareaPorId(Integer tareaId) {
        return null;
    }

    @Override
    public TareaDTO actualizarTarea(Integer tareaId, TareaDTO tareaDTO) {
        return null;
    }

    @Override
    public void eliminarTarea(Integer tareaId) {

    }

    @Override
    public void moverTarea(Integer tareaId, Integer nuevaTablaId) {

    }

    @Override
    public void cambiarEstado(Integer tareaId, String nuevoEstado) {

    }

    @Override
    public void cambiarPrioridad(Integer tareaId, String nuevaPrioridad) {

    }

    @Override
    public void añadirEtiqueta(Integer tareaId, Integer etiquetaId) {

    }

    @Override
    public void quitarEtiqueta(Integer tareaId, Integer etiquetaId) {

    }

    @Override
    public void asignarUsuario(Integer tareaId, Integer usuarioId) {

    }

    @Override
    public void desasignarUsuario(Integer tareaId, Integer usuarioId) {

    }

    @Override
    public void actualizarFechaLimite(Integer tareaId, Date nuevaFechaLimite) {

    }

    @Override
    public List<TareaDTO> listarTareasPorTabla(Integer tablaId) {
        return null;
    }

    @Override
    public List<TareaDTO> listarTareasAsignadasAlUsuario() {
        return null;
    }
}
