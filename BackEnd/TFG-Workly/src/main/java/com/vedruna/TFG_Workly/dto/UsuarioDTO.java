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
    private Integer id;
    private String nombre;
    private String email;
    private String foto;
    private String rol;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getUsuarioId();
        this.nombre = usuario.getNombre();
        this.email = usuario.getEmail();
        this.foto = usuario.getFoto();
        this.rol = String.valueOf(usuario.getUserRol());
    }

}
