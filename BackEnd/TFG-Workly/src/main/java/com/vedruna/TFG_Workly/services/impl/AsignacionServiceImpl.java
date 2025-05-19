package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.AsignacionDTO;
import com.vedruna.TFG_Workly.models.Asignacion;
import com.vedruna.TFG_Workly.models.Tarea;
import com.vedruna.TFG_Workly.models.Usuario;
import com.vedruna.TFG_Workly.repositories.IAsignacionRepository;
import com.vedruna.TFG_Workly.repositories.ITareaRepository;
import com.vedruna.TFG_Workly.repositories.IUsuarioRepository;
import com.vedruna.TFG_Workly.services.AsignacionServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignacionServiceImpl implements AsignacionServiceI {

    @Autowired
    private IAsignacionRepository asignacionRepository;

    @Autowired
    private ITareaRepository tareaRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public AsignacionDTO asignarTareaAUsuario(Integer tareaId, Integer usuarioId) {
        // Verifica si ya está asignada
        if (asignacionRepository.existsByTarea_TareaIdAndUsuario_UsuarioId(tareaId, usuarioId)) {
            throw new IllegalArgumentException("El usuario ya está asignado a esta tarea.");
        }

        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Asignacion asignacion = new Asignacion();
        asignacion.setTarea(tarea);
        asignacion.setUsuario(usuario);

        asignacionRepository.save(asignacion);
        return new AsignacionDTO(asignacion);
    }

    @Override
    public List<AsignacionDTO> obtenerAsignacionesDeTarea(Integer tareaId) {
        return asignacionRepository.findByTarea_TareaId(tareaId)
                .stream()
                .map(AsignacionDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AsignacionDTO> obtenerAsignacionesDeUsuario(Integer usuarioId) {
        return asignacionRepository.findByUsuario_UsuarioId(usuarioId)
                .stream()
                .map(AsignacionDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarAsignacion(Integer asignacionId) {
        if (!asignacionRepository.existsById(asignacionId)) {
            throw new EntityNotFoundException("Asignación no encontrada");
        }
        asignacionRepository.deleteById(asignacionId);
    }
}


