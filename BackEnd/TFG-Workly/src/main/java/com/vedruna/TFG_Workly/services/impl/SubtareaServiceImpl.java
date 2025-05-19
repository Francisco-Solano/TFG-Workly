package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.SubtareaDTO;
import com.vedruna.TFG_Workly.models.Subtarea;
import com.vedruna.TFG_Workly.models.Tarea;
import com.vedruna.TFG_Workly.repositories.ISubtareaRepository;
import com.vedruna.TFG_Workly.repositories.ITareaRepository;
import com.vedruna.TFG_Workly.services.SubtareaServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubtareaServiceImpl implements SubtareaServiceI {

    @Autowired
    private ISubtareaRepository subtareaRepository;

    @Autowired
    private ITareaRepository tareaRepository;

    @Override
    public SubtareaDTO crearSubtarea(Integer tareaId, SubtareaDTO subtareaDTO) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con ID: " + tareaId));

        Subtarea subtarea = new Subtarea();
        subtarea.setTitulo(subtareaDTO.getTitulo());
        subtarea.setEstado(subtareaDTO.getEstado());
        subtarea.setTarea(tarea);

        Subtarea guardada = subtareaRepository.save(subtarea);
        return new SubtareaDTO(guardada);
    }

    @Override
    public SubtareaDTO obtenerSubtareaPorId(Integer subtareaId) {
        Subtarea subtarea = subtareaRepository.findById(subtareaId)
                .orElseThrow(() -> new EntityNotFoundException("Subtarea no encontrada con ID: " + subtareaId));
        return new SubtareaDTO(subtarea);
    }

    @Override
    public SubtareaDTO actualizarSubtarea(Integer subtareaId, SubtareaDTO subtareaDTO) {
        Subtarea subtarea = subtareaRepository.findById(subtareaId)
                .orElseThrow(() -> new EntityNotFoundException("Subtarea no encontrada con ID: " + subtareaId));

        if (subtareaDTO.getTitulo() != null) {
            subtarea.setTitulo(subtareaDTO.getTitulo());
        }
        if (subtareaDTO.getEstado() != null) {
            subtarea.setEstado(subtareaDTO.getEstado());
        }

        Subtarea actualizada = subtareaRepository.save(subtarea);
        return new SubtareaDTO(actualizada);
    }

    @Override
    public void cambiarEstado(Integer subtareaId, String nuevoEstado) {
        Subtarea subtarea = subtareaRepository.findById(subtareaId)
                .orElseThrow(() -> new EntityNotFoundException("Subtarea no encontrada con ID: " + subtareaId));

        subtarea.setEstado(nuevoEstado);
        subtareaRepository.save(subtarea);
    }

    @Override
    public void eliminarSubtarea(Integer subtareaId) {
        if (!subtareaRepository.existsById(subtareaId)) {
            throw new EntityNotFoundException("Subtarea no encontrada con ID: " + subtareaId);
        }
        subtareaRepository.deleteById(subtareaId);
    }

    @Override
    public List<SubtareaDTO> listarSubtareasPorTarea(Integer tareaId) {
        List<Subtarea> subtareas = subtareaRepository.findByTarea_TareaId(tareaId);
        return subtareas.stream().map(SubtareaDTO::new).collect(Collectors.toList());
    }
}


