package com.vedruna.TFG_Workly.repositories;

import com.vedruna.TFG_Workly.models.Comentario;
import com.vedruna.TFG_Workly.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IComentarioRepository extends JpaRepository<Comentario,Integer> {
    List<Comentario> findByTareaId(Integer tareaId);
    List<Comentario> findByUsuario_UsuarioId(Integer usuarioId);

}
