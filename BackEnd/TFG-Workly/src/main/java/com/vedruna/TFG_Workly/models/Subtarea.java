package com.vedruna.TFG_Workly.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "subtareas")
@Getter
@Setter
@NoArgsConstructor
public class Subtarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subtarea_id")
    private Integer subtareaId;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "tarea_id", referencedColumnName = "tarea_id", nullable = false)
    private Tarea tarea;




}
