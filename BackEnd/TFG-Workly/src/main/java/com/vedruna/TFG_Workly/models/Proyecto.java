package com.vedruna.TFG_Workly.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "proyectos")
@Getter
@Setter
@NoArgsConstructor
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proyecto_id")
    private Integer proyectoId;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "visibilidad", nullable = false)
    private boolean visibilidad;

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "usuario_id", nullable = false)
    private Usuario admin;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private List<Tabla> tablas;
}


