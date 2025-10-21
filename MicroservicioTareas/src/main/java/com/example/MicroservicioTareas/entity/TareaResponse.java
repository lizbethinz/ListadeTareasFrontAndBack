package com.example.MicroservicioTareas.entity;

import java.time.LocalDate;


public class TareaResponse {
    private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Long getIdUsuario() {
        return idUsuario;
    }
    private Boolean completada;
    public Boolean getCompletada() {
        return completada;
    }
    public void setCompletada(Boolean completada) {
        this.completada = completada;
    }
    private Boolean cancelada;
    public Boolean getCancelada() {
        return cancelada;
    }
    public void setCancelada(Boolean cancelada) {
        this.cancelada = cancelada;
    }
    private LocalDate fechaCreacion;

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    private LocalDate fechaCancelacion;

    public LocalDate getFechaCancelacion() {
        return fechaCancelacion;
    }
    public void setFechaCancelacion(LocalDate fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }
    private LocalDate fechaFinalizacion;

    public LocalDate getFechaFinalizacion() {
        return fechaFinalizacion;
    }
    public void setFechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }
    public LocalDate fechaObjetivo;
    public LocalDate getFechaObjetivo() {
        return fechaObjetivo;
    }
    public void setFechaObjetivo(LocalDate fechaObjetivo) {
        this.fechaObjetivo = fechaObjetivo;
    }


}
