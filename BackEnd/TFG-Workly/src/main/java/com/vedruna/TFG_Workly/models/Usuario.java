package com.vedruna.TFG_Workly.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "contraseña", nullable = false)
    private String contraseña;

    @Column(name = "rol", nullable = false)
    private String rol;

    @Column(name = "foto")
    private String foto;

    // Puedes mejorar esto luego con roles reales
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // sin roles por ahora
    }

    @Override
    public String getPassword() {
        return this.contraseña;
    }

    @Override
    public String getUsername() {
        return this.nombre; // o usa `nombre` si prefieres
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
