package com.vedruna.TFG_Workly.repositories;

import com.vedruna.TFG_Workly.models.Asignacion;
import com.vedruna.TFG_Workly.models.Tarea;
import com.vedruna.TFG_Workly.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAsignacionRepository extends JpaRepository<Asignacion,Integer> {
    List<Asignacion> findByTarea_TareaId(Integer tareaId);
    Boolean existsByTarea_TareaIdAndUsuario_UsuarioId(Integer tareaId, Integer usuarioId);

    List<Asignacion> findByUsuario_UsuarioId(Integer usuarioId);




}


