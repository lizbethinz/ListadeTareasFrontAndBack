package com.example.MicroservicioTareas.entity;

import java.time.LocalDate;

public class GeneraTareaRequest {
    private String nombre;
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    private String descripcion;
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    private Long idUsuario;
     public Long getIdUsuario() {
        return idUsuario;
    }
    LocalDate fechaObjetivo;
    public LocalDate getFechaObjetivo() {
        return fechaObjetivo;
    }
    public void setFechaObjetivo(LocalDate fechaObjetivo) {
        this.fechaObjetivo = fechaObjetivo;
    } 


}
