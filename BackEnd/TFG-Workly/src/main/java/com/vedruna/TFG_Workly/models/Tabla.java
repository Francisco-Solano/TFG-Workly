package com.vedruna.TFG_Workly.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tablas")
@Getter
@Setter
@NoArgsConstructor
public class Tabla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tabla_id")
    private Integer tablaId;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "posicion", nullable = false)
    private int posicion;

    @Column(name = "color")
    private String color;

    @ManyToOne
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;

    @OneToMany(mappedBy = "tabla", cascade = CascadeType.ALL)
    private List<Tarea> tareas;


}










