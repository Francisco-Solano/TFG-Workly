package com.vedruna.TFG_Workly.dto;

import com.vedruna.TFG_Workly.models.Rol;
import com.vedruna.TFG_Workly.models.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioDTO {
    private String username;
    private String email;
    private String foto;
    private String rol;

    public UsuarioDTO(Usuario usuario) {
        this.username = usuario.getUsername();
        this.email = usuario.getEmail();
        this.foto = usuario.getFoto();
        this.rol = usuario.getUserRol().getRolName();

    }

}