package com.example.MicroservicioTareas.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MicroservicioTareas.entity.Usuario;
import com.example.MicroservicioTareas.service.IUsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

      IUsuarioService service;
    
    @GetMapping("/home")
    public String home() {
        return "Bienvenido al Micoservicio de Tareas!";
        
    }

    
    

      @GetMapping("/usuarios")
    public List<Usuario>getAll(){
        return service.getAll();
    }


}
