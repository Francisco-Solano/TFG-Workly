package com.vedruna.TFG_Workly.repositories;

import com.vedruna.TFG_Workly.models.Proyecto;
import com.vedruna.TFG_Workly.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProyectoRepository extends JpaRepository<Proyecto, Integer> {

    // Buscar proyectos donde el admin tenga el usuarioId dado
    List<Proyecto> findByAdminUsuarioId(Integer usuarioId);

    // Buscar proyectos por visibilidad
    List<Proyecto> findByVisibilidad(Boolean visibilidad);

    // Buscar proyectos donde haya un colaborador con ese usuarioId
    List<Proyecto> findByColaboradoresUsuarioUsuarioId(Integer usuarioId);

}
