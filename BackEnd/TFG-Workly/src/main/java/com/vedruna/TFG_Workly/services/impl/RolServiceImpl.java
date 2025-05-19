package com.vedruna.TFG_Workly.services.impl;


import com.vedruna.TFG_Workly.dto.RolDTO;
import com.vedruna.TFG_Workly.repositories.IRolRepository;
import com.vedruna.TFG_Workly.services.RolServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class RolServiceImpl implements RolServiceI {

    @Autowired
    private IRolRepository rolRepository;

    @Override
    public RolDTO selectRolById(Integer id) {
        return rolRepository.findById(id)
                .map(RolDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + id));
    }

    @Override
    public RolDTO selectRolByName(String rolName) {
        return rolRepository.findByRolName(rolName)
                .map(RolDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con nombre: " + rolName));
    }
}



