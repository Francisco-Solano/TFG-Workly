package com.vedruna.TFG_Workly.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "etiquetas")
@Getter
@Setter
@NoArgsConstructor
public class Etiqueta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "etiqueta_id")
    private Integer etiquetaId;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "color")
    private String color;

    @ManyToMany(mappedBy = "etiquetas")
    private List<Tarea> tareas;


}
