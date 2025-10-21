package com.example.MicroservicioTareas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.MicroservicioTareas.entity.ActualizaTareaRequest;
import com.example.MicroservicioTareas.entity.GeneraTareaRequest;
import com.example.MicroservicioTareas.entity.Tarea;
import com.example.MicroservicioTareas.entity.Usuario;
import com.example.MicroservicioTareas.repository.TareaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TareaService implements ITareaService {

    @Autowired
        TareaRepository repository;

        @Autowired
        private UsuarioService usuarioRepository;

    @Override
    public List<Tarea> gettareasbyUsuario(Long idUsuario) {
        return
        repository.findAllByIdUsuario_Id(idUsuario);
       
    }

     @Transactional
    public Tarea save(Tarea tarea) {

        return repository.save(tarea);
    }

     @Override
     public Tarea registrarTarea(GeneraTareaRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario());
    if (usuario == null) {
        throw new EntityNotFoundException("Usuario no encontrado con ID: " + request.getIdUsuario());
    }
        Tarea tareita = new Tarea();
        tareita.setNombre(request.getNombre());
        tareita.setDescripcion(request.getDescripcion());
        tareita.setIdUsuario(usuario);
        tareita.setCancelada(false);
        tareita.setCompletada(false);
        tareita.setFechaCreacion(LocalDate.now());
        tareita.setFechaObjetivo(request.getFechaObjetivo());

        return repository.save(tareita);
    }

     @Override
     public Tarea actualizarTarea(ActualizaTareaRequest request) {
        Tarea tarea = repository.findById(request.getId())
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + request.getId()));

            //actualizaremos los campos emviados
            tarea.setNombre(request.getNombre());
            tarea.setDescripcion(request.getDescripcion());
            tarea.setCompletada(request.isCompletada());
            tarea.setCancelada(request.isCancelada());
            if (request.getFechaObjetivo() != null) {
                tarea.setFechaObjetivo(request.getFechaObjetivo());
            } else {
                tarea.setFechaObjetivo(null);
            }

            if (request.isCompletada() && tarea.getFechaFinalizacion() == null) {
                tarea.setFechaFinalizacion(LocalDate.now());
            }
            if(request.isCancelada() && tarea.getFechaCancelacion()==null){
                tarea.setFechaCancelacion(LocalDate.now());
            }

            return repository.save(tarea);
     }
     @Override
     public List<Tarea> getTareasActivasByUsuario(Long idUsuario) {
        return repository.findByIdUsuarioIdAndCompletadaFalseAndCanceladaFalse(idUsuario);
    }

     @Override
     public List<Tarea> obtenerTareasFiltradas(Long idUsuario, String estado, LocalDate fechaInicio,
            LocalDate fechaFin) {
        if (estado != null && estado.equalsIgnoreCase("TODAS")) {
        estado = null; 
    }
     return repository.findTareasByFilters(idUsuario, estado, fechaInicio, fechaFin);     }

}
