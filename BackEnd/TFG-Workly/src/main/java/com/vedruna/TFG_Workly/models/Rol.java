package com.vedruna.TFG_Workly.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id", nullable = false)
    Integer rolId;

    @NotBlank(message = "Rol name is required and must not be blank")
    @Size(max = 45, message = "Rol name must be at most 45 characters long")
    @Column(name = "rol_name", nullable = false, unique = true)
    String rolName;

    @OneToMany(mappedBy = "userRol",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Usuario> usersWithThisRol;
}