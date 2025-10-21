package com.example.MicroservicioTareas.entity;

import java.time.LocalDate;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActualizaTareaRequest {
    public Long id;
    public String nombre;
    public String descripcion;
    public boolean completada;
    public boolean cancelada;
    public LocalDate fechaObjetivo;

}
