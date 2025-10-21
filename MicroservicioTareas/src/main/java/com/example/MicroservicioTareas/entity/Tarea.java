package com.example.MicroservicioTareas.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Table(name="tarea")
@AllArgsConstructor
@Data
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private String nombre;

    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario idUsuario;
    
    private boolean completada;
    
    private boolean cancelada;
    
    private LocalDate fechaCreacion;

    private LocalDate fechaFinalizacion;

    private LocalDate fechaCancelacion;

    private LocalDate fechaObjetivo;

    public Tarea() {
    }

}
