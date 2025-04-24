package com.vedruna.TFG_Workly.repositories;

import com.vedruna.TFG_Workly.models.Colaborador;
import com.vedruna.TFG_Workly.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IColaboradorRepository extends JpaRepository<Colaborador,Integer> {
    List<Colaborador> findByProyecto_ProyectoId(Integer proyectoId);
    List<Colaborador> findByUsuario_UsuarioId(Integer usuarioId);
    Boolean existsByProyecto_ProyectoIdAndUsuario_UsuarioId(Integer proyectoId, Integer usuarioId);

}
