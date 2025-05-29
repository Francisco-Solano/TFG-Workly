package com.vedruna.TFG_Workly.repositories;

import com.vedruna.TFG_Workly.models.Colaborador;
import com.vedruna.TFG_Workly.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IColaboradorRepository extends JpaRepository<Colaborador,Integer> {
    List<Colaborador> findByProyecto_ProyectoId(Integer proyectoId);
   // Optional<Colaborador> findByProyecto_ProyectoIdAndProyecto_Usuario_UsuarioId(Integer proyectoId, Integer usuarioId);
    Optional<Colaborador> findByProyecto_ProyectoIdAndUsuario_UsuarioId(Integer proyectoId, Integer usuarioId);

    Boolean existsByProyecto_ProyectoIdAndProyecto_Usuario_UsuarioId(Integer proyectoId, Integer usuarioId);
    List<Colaborador> findByUsuario_UsuarioId(Integer usuarioId);


}


