package com.example.MicroservicioTareas.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.MicroservicioTareas.entity.Tarea;

@Repository
public interface TareaRepository extends CrudRepository<Tarea, Long>{

    public List<Tarea> findAllByIdUsuario_Id(Long id);

    public List<Tarea> findByIdUsuarioIdAndCompletadaFalseAndCanceladaFalse(Long idUsuario);

}
