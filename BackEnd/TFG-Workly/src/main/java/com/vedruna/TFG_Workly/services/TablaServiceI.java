package com.vedruna.TFG_Workly.services;

import com.vedruna.TFG_Workly.dto.TablaDTO;

import java.util.List;

public interface TablaServiceI {
    // Crear una nueva tabla dentro de un proyecto
    TablaDTO crearTabla(Integer proyectoId, TablaDTO tablaDTO);

    // Obtener una tabla por su ID
    TablaDTO obtenerTablaPorId(Integer tablaId);

    // Listar todas las tablas de un proyecto
    List<TablaDTO> listarTablasPorProyecto(Integer proyectoId);

    // Actualizar el nombre, color o posición de la tabla
    TablaDTO actualizarTabla(Integer tablaId, TablaDTO tablaDTO);

    // Cambiar la posición (para ordenarlas en el frontend tipo Trello)
    void cambiarPosicion(Integer tablaId, int nuevaPosicion);

    // Eliminar una tabla
    void eliminarTabla(Integer tablaId);
}


