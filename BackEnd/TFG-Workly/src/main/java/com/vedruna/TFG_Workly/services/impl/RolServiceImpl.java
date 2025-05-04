package com.vedruna.TFG_Workly.services.impl;


import com.vedruna.TFG_Workly.dto.RolDTO;
import com.vedruna.TFG_Workly.repositories.IRolRepository;
import com.vedruna.TFG_Workly.services.RolServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

public class RolServiceImpl implements RolServiceI {

    @Override
    public RolDTO selectRolById(Integer id) {
        return null;
    }

    @Override
    public RolDTO selectRolByName(String rolName) {
        return null;
    }
}

