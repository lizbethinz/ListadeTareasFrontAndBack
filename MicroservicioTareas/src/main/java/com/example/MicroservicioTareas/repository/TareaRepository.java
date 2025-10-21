package com.example.MicroservicioTareas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.MicroservicioTareas.entity.Tarea;

@Repository
public interface TareaRepository extends CrudRepository<Tarea, Long>{

    public List<Tarea> findAllByIdUsuario_Id(Long id);

    public List<Tarea> findByIdUsuarioIdAndCompletadaFalseAndCanceladaFalse(Long idUsuario);


    @Query("SELECT t FROM Tarea t WHERE " +
            "t.idUsuario.id = :idUsuario AND " + 
            "(" +
                "(:estado IS NULL) OR " + 
                "(:estado = 'COMPLETADA' AND t.completada = true AND t.cancelada = false) OR " +
                "(:estado = 'CANCELADA' AND t.cancelada = true) OR " +
                "(:estado = 'ACTIVA' AND t.completada = false AND t.cancelada = false)" +
            ") AND " +
            "(:fechaInicio IS NULL OR t.fechaCreacion >= :fechaInicio) AND " +
            "(:fechaFin IS NULL OR t.fechaCreacion <= :fechaFin)"
        )List<Tarea> findTareasByFilters(
            @Param("idUsuario") Long idUsuario, 
            @Param("estado") String estado, 
            @Param("fechaInicio") LocalDate fechaInicio, 
            @Param("fechaFin") LocalDate fechaFin
            );
}
