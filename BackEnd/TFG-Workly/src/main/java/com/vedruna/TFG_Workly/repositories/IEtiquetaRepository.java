package com.vedruna.TFG_Workly.repositories;

import com.vedruna.TFG_Workly.models.Etiqueta;
import com.vedruna.TFG_Workly.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEtiquetaRepository extends JpaRepository<Etiqueta,Integer> {
    List<Etiqueta> findByNombreContainingIgnoreCase(String nombre);
    List<Etiqueta> findByColor(String color);

}
