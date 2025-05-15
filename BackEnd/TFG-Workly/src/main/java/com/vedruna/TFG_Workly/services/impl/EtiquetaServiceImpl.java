package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.CrearTareaDTO;
import com.vedruna.TFG_Workly.dto.EtiquetaDTO;
import com.vedruna.TFG_Workly.dto.TareaDTO;
import com.vedruna.TFG_Workly.dto.UsuarioDTO;
import com.vedruna.TFG_Workly.models.Etiqueta;
import com.vedruna.TFG_Workly.models.Tabla;
import com.vedruna.TFG_Workly.models.Tarea;
import com.vedruna.TFG_Workly.models.Usuario;
import com.vedruna.TFG_Workly.repositories.IEtiquetaRepository;
import com.vedruna.TFG_Workly.repositories.ITareaRepository;
import com.vedruna.TFG_Workly.services.EtiquetaServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EtiquetaServiceImpl implements EtiquetaServiceI {
    @Autowired
    IEtiquetaRepository etiquetaRepository;
    @Autowired
    ITareaRepository tareaRepository;
    @Override
    public EtiquetaDTO crearEtiqueta(EtiquetaDTO etiquetaDTO) {
        Etiqueta etiqueta = new Etiqueta();
        etiqueta.setNombre(etiquetaDTO.getNombre());
        etiqueta.setColor(etiquetaDTO.getColor());

        Etiqueta etiquetaGuardada = etiquetaRepository.save(etiqueta);

        return new EtiquetaDTO(etiquetaGuardada);
    }





    @Override
    public EtiquetaDTO obtenerEtiquetaPorId(Integer etiquetaId) {
        Etiqueta etiqueta = etiquetaRepository.findById(etiquetaId).orElseThrow(() -> new EntityNotFoundException("Etiaueta no encontrada"));
        return new EtiquetaDTO(etiqueta);
    }


    @Override
    public List<EtiquetaDTO> listarEtiquetas() {
        List<Etiqueta> etiquetas = etiquetaRepository.findAll();
        return etiquetas.stream().map(EtiquetaDTO::new).collect(Collectors.toList());
    }

    @Override
    public EtiquetaDTO actualizarEtiqueta(Integer etiquetaId, EtiquetaDTO etiquetaDTO) {
        Etiqueta etiqueta = etiquetaRepository.findById(etiquetaId)
                .orElseThrow(() -> new EntityNotFoundException("Etiqueta no encontrada con ID: " + etiquetaId));

        // Actualizar nombre y color si se proporcionan
        if (etiquetaDTO.getNombre() != null && !etiquetaDTO.getNombre().isBlank()) {
            etiqueta.setNombre(etiquetaDTO.getNombre());
        }

        if (etiquetaDTO.getColor() != null && !etiquetaDTO.getColor().isBlank()) {
            etiqueta.setColor(etiquetaDTO.getColor());
        }

        Etiqueta etiquetaActualizada = etiquetaRepository.save(etiqueta);
        return new EtiquetaDTO(etiquetaActualizada);
    }

    @Override
    public void eliminarEtiqueta(Integer etiquetaId) {
        Etiqueta etiqueta = etiquetaRepository.findById(etiquetaId).orElseThrow(() -> new EntityNotFoundException("Etiqueta no encontrada"));
        etiquetaRepository.delete(etiqueta);
    }

    @Override
    public void asignarEtiquetaATarea(Integer etiquetaId, Integer tareaId) {
        Etiqueta etiqueta = etiquetaRepository.findById(etiquetaId).orElseThrow(() -> new EntityNotFoundException("Etiqueta no encontrada"));
        Tarea tarea = tareaRepository.findById(tareaId).orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
        // Inicializar la lista si está vacía (precaución por NullPointer)
        if (tarea.getEtiquetas() == null) {
            tarea.setEtiquetas((Set<Etiqueta>) etiqueta);
        }

        // Evitar añadir duplicados
        if (!tarea.getEtiquetas().contains(etiqueta)) {
            tarea.getEtiquetas().add(etiqueta);
            tareaRepository.save(tarea); // Persistir el cambio
        }
    }

    @Override
    public void quitarEtiquetaDeTarea(Integer etiquetaId, Integer tareaId) {
        Etiqueta etiqueta = etiquetaRepository.findById(etiquetaId)
                .orElseThrow(() -> new EntityNotFoundException("Etiqueta no encontrada"));

        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));

        List<Etiqueta> etiquetas = (List<Etiqueta>) tarea.getEtiquetas();

        if (etiquetas != null && etiquetas.contains(etiqueta)) {
            etiquetas.remove(etiqueta);
            tareaRepository.save(tarea); // Persistir el cambio
        }
    }
}
