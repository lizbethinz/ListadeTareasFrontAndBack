package com.example.MicroservicioTareas.service;

import java.time.LocalDate;
import java.util.List;

import com.example.MicroservicioTareas.entity.ActualizaTareaRequest;
import com.example.MicroservicioTareas.entity.GeneraTareaRequest;
import com.example.MicroservicioTareas.entity.Tarea;

public interface ITareaService {

    List<Tarea> gettareasbyUsuario(Long idUsuario);

    Tarea save(Tarea tarea);

    Tarea registrarTarea(GeneraTareaRequest tarea);

    Tarea actualizarTarea(ActualizaTareaRequest request);

    List<Tarea> getTareasActivasByUsuario(Long idUsuario);

    List<Tarea> obtenerTareasFiltradas(Long idUsuario, String estado, LocalDate fechaInicio, LocalDate fechaFin);

}
