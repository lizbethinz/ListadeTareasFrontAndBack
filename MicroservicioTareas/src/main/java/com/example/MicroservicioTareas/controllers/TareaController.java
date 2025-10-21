package com.example.MicroservicioTareas.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MicroservicioTareas.entity.ActualizaTareaRequest;
import com.example.MicroservicioTareas.entity.GeneraTareaRequest;
import com.example.MicroservicioTareas.entity.Tarea;
import com.example.MicroservicioTareas.entity.TareaResponse;
import com.example.MicroservicioTareas.service.ITareaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/tareas")
@EnableMethodSecurity
@RequiredArgsConstructor
public class TareaController {

    private final ITareaService service;

    @GetMapping("/listado/{idUsuario}")
    public List<TareaResponse> gettareasbyUsuario(@PathVariable Long idUsuario){
         List<Tarea> tareas =service.gettareasbyUsuario(idUsuario);

    return tareas.stream()
        .map(tarea -> {
           
            TareaResponse responseDTO = new TareaResponse();
            responseDTO.setId(tarea.getId());
            responseDTO.setNombre(tarea.getNombre());
            responseDTO.setIdUsuario(tarea.getIdUsuario().getId());
            responseDTO.setDescripcion(tarea.getDescripcion());
            responseDTO.setCompletada(tarea.isCompletada());
            responseDTO.setCancelada(tarea.isCancelada());
            responseDTO.setFechaCreacion(tarea.getFechaCreacion());
            responseDTO.setFechaCancelacion(tarea.getFechaCancelacion());
            responseDTO.setFechaFinalizacion(tarea.getFechaFinalizacion());
            responseDTO.setFechaObjetivo(tarea.getFechaObjetivo());

            
            return responseDTO;
        })
        .collect(Collectors.toList());
    }

    //listado de las que están activas
    @GetMapping("/listado/activas/{idUsuario}")
    public List<TareaResponse> gettareasActivasbyUsuario(@PathVariable Long idUsuario){
         List<Tarea> tareas =service.getTareasActivasByUsuario(idUsuario);

    return tareas.stream()
        .map(tarea -> {
           
            TareaResponse responseDTO = new TareaResponse();
            responseDTO.setId(tarea.getId());
            responseDTO.setNombre(tarea.getNombre());
            responseDTO.setIdUsuario(tarea.getIdUsuario().getId());
            responseDTO.setDescripcion(tarea.getDescripcion());
            responseDTO.setCompletada(tarea.isCompletada());
            responseDTO.setCancelada(tarea.isCancelada());
            responseDTO.setFechaCreacion(tarea.getFechaCreacion());
            responseDTO.setFechaCancelacion(tarea.getFechaCancelacion());
            responseDTO.setFechaFinalizacion(tarea.getFechaFinalizacion());
            responseDTO.setFechaObjetivo(tarea.getFechaObjetivo());

            
            return responseDTO;
        })
        .collect(Collectors.toList());
    }

    @PostMapping("/nuevatarea")
     public ResponseEntity<?> save(@RequestBody GeneraTareaRequest tarea){

        try {
            Tarea nuevaTarea = service.registrarTarea(tarea);
        Tarea tareaRegistrada = service.save(nuevaTarea);
            return new ResponseEntity<>(tareaRegistrada.getNombre() + " registrada exitosamente.", HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // 409
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno al registrar la tarea.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
     }
     
     @PutMapping("/actualizatarea")
     public ResponseEntity<?> actualizaTarea(@RequestBody ActualizaTareaRequest request) {
                try {
                    Tarea tareaActualizada = service.actualizarTarea(request);
                    TareaResponse respuesta = new TareaResponse();
                    respuesta.setId(tareaActualizada.getId());
                    respuesta.setNombre(tareaActualizada.getNombre());
                    respuesta.setIdUsuario(tareaActualizada.getIdUsuario().getId());
                    respuesta.setDescripcion(tareaActualizada.getDescripcion());
                    respuesta.setCompletada(tareaActualizada.isCompletada());
                    respuesta.setCancelada(tareaActualizada.isCancelada());
                    respuesta.setFechaCreacion(tareaActualizada.getFechaCreacion());
                    respuesta.setFechaCancelacion(tareaActualizada.getFechaCancelacion());
                    respuesta.setFechaFinalizacion(tareaActualizada.getFechaFinalizacion());
                    respuesta.setFechaObjetivo(tareaActualizada.getFechaObjetivo());
                    return ResponseEntity.ok(respuesta);
                }catch(RuntimeException e){
                     return new ResponseEntity<>("Error al actualizar la tarea.", HttpStatus.NOT_FOUND);

                }
     }
     

}
