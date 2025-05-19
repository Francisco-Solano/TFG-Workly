package com.vedruna.TFG_Workly.services;

import com.vedruna.TFG_Workly.dto.RolDTO;

public interface RolServiceI {
    RolDTO selectRolById(Integer id);
    RolDTO selectRolByName(String rolName);
}

