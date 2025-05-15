package com.vedruna.TFG_Workly.repositories;

import com.vedruna.TFG_Workly.models.Archivo;
import com.vedruna.TFG_Workly.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IArchivoRepository extends JpaRepository<Archivo,Integer> {
    List<Archivo> findByTarea(Integer tareaId);

}
