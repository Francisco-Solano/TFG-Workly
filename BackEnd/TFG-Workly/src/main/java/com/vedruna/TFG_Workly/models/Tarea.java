package com.vedruna.TFG_Workly.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tareas")
@Getter
@Setter
@NoArgsConstructor
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tarea_id")
    private Integer tareaId;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado")
    private String estado; // Podrías usar Enum

    @Column(name = "prioridad")
    private String prioridad; // También recomendable usar Enum

    @ManyToOne
    @JoinColumn(name = "proyecto_id") // Asegúrate que este sea el nombre correcto de la columna
    private Proyecto proyecto;


    @Column(name = "fecha_limite")
    private Date fechaLimite;

    @Column(name = "posicion")
    private Integer posicion;

    @ManyToOne
    @JoinColumn(name = "tabla_id", nullable = false)
    private Tabla tabla;

    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL)
    private List<Subtarea> subtareas;

    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL)
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL)
    private List<Archivo> archivos;

    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL)
    private List<Asignacion> asignaciones;



/*
    @ManyToMany
    @JoinTable(
            name = "tarea_etiqueta",  // Cambiado para evitar conflicto
            joinColumns = @JoinColumn(name = "tarea_id"),
            inverseJoinColumns = @JoinColumn(name = "etiqueta_id")
    )


   private Set<Etiqueta> etiquetas = new HashSet<>();


 */

}

