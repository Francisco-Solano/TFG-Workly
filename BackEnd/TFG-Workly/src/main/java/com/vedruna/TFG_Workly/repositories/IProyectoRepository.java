package com.vedruna.TFG_Workly.repositories;

import com.vedruna.TFG_Workly.models.Proyecto;
import com.vedruna.TFG_Workly.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProyectoRepository extends JpaRepository<Proyecto, Integer> {
    List<Proyecto> findByAdminId_UsuarioId(Integer usuarioId);
    List<Proyecto> findByVisibilidad(Boolean visibilidad);
    List<Proyecto> findByColaboradores_Usuario_UsuarioId(Integer usuarioId); // si tienes @OneToMany en colaborador

}
