package com.vedruna.TFG_Workly.repositories;

import com.vedruna.TFG_Workly.models.Tabla;
import com.vedruna.TFG_Workly.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITablaRepository extends JpaRepository<Tabla,Integer> {
    List<Tabla> findByProyecto_ProyectoId(Integer proyectoId);
    List<Tabla> findByProyectoProyectoId(Integer proyectoId);

}


