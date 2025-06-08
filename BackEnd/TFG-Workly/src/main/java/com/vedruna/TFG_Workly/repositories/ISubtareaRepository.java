package com.vedruna.TFG_Workly.repositories;

import com.vedruna.TFG_Workly.models.Subtarea;
import com.vedruna.TFG_Workly.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubtareaRepository extends JpaRepository<Subtarea,Integer> {
    List<Subtarea> findByTarea_TareaId(Integer tareaId);


}
