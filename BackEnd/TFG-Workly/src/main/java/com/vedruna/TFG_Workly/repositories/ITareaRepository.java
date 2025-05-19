package com.vedruna.TFG_Workly.repositories;

import com.vedruna.TFG_Workly.models.Tarea;
import com.vedruna.TFG_Workly.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ITareaRepository extends JpaRepository<Tarea,Integer> {
    List<Tarea> findByTabla_TablaId(Integer tablaId);
    List<Tarea> findByProyecto_ProyectoId(Integer proyectoId);
    List<Tarea> findByEstado(String estado);
    List<Tarea> findByPrioridad(String prioridad);
    List<Tarea> findByFechaLimiteBefore(Date fecha);
    List<Tarea> findByTituloContainingIgnoreCase(String texto); // búsqueda por título


}


