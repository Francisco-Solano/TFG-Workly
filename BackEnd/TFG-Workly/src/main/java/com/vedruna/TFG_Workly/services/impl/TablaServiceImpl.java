package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.TablaDTO;
import com.vedruna.TFG_Workly.models.Proyecto;
import com.vedruna.TFG_Workly.models.Tabla;
import com.vedruna.TFG_Workly.repositories.IProyectoRepository;
import com.vedruna.TFG_Workly.repositories.ITablaRepository;
import com.vedruna.TFG_Workly.services.TablaServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TablaServiceImpl implements TablaServiceI {

    @Autowired
    private ITablaRepository tablaRepository;

    @Autowired
    private IProyectoRepository proyectoRepository;

    @Override
    public TablaDTO crearTabla(Integer proyectoId, TablaDTO tablaDTO) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado con ID: " + proyectoId));

        Tabla tabla = new Tabla();
        tabla.setNombre(tablaDTO.getNombre());
        tabla.setPosicion(tablaDTO.getPosicion());
        tabla.setColor(tablaDTO.getColor());
        tabla.setProyecto(proyecto);

        Tabla guardada = tablaRepository.save(tabla);
        return new TablaDTO(guardada);
    }

    @Override
    public TablaDTO obtenerTablaPorId(Integer tablaId) {
        Tabla tabla = tablaRepository.findById(tablaId)
                .orElseThrow(() -> new EntityNotFoundException("Tabla no encontrada con ID: " + tablaId));
        return new TablaDTO(tabla);
    }

    @Override
    public List<TablaDTO> listarTablasPorProyecto(Integer proyectoId) {
        List<Tabla> tablas = tablaRepository.findByProyecto_ProyectoId(proyectoId);
        return tablas.stream().map(TablaDTO::new).collect(Collectors.toList());
    }

    @Override
    public TablaDTO actualizarTabla(Integer tablaId, TablaDTO tablaDTO) {
        Tabla tabla = tablaRepository.findById(tablaId)
                .orElseThrow(() -> new EntityNotFoundException("Tabla no encontrada con ID: " + tablaId));

        if (tablaDTO.getNombre() != null) tabla.setNombre(tablaDTO.getNombre());
        if (tablaDTO.getColor() != null) tabla.setColor(tablaDTO.getColor());
        tabla.setPosicion(tablaDTO.getPosicion());

        Tabla actualizada = tablaRepository.save(tabla);
        return new TablaDTO(actualizada);
    }

    @Override
    public void cambiarPosicion(Integer tablaId, int nuevaPosicion) {
        Tabla tabla = tablaRepository.findById(tablaId)
                .orElseThrow(() -> new EntityNotFoundException("Tabla no encontrada con ID: " + tablaId));

        tabla.setPosicion(nuevaPosicion);
        tablaRepository.save(tabla);
    }

    @Override
    public void eliminarTabla(Integer tablaId) {
        if (!tablaRepository.existsById(tablaId)) {
            throw new EntityNotFoundException("Tabla no encontrada con ID: " + tablaId);
        }
        tablaRepository.deleteById(tablaId);
    }
}


