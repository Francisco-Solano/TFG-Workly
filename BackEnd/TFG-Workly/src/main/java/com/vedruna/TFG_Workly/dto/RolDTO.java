package com.vedruna.TFG_Workly.dto;

import com.vedruna.TFG_Workly.models.Rol;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RolDTO {
    Integer rolId;
    String rolName;

    public RolDTO(Rol rol) {
        this.rolId = rol.getRolId();
        this.rolName = rol.getRolName();
    }
}


