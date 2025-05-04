package com.vedruna.TFG_Workly.repositories;

import com.vedruna.TFG_Workly.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolName(String rolName);
}


