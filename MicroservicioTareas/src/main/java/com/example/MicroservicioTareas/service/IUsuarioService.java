package com.example.MicroservicioTareas.service;

import java.util.List;

import com.example.MicroservicioTareas.entity.Usuario;

public interface IUsuarioService {

    List<Usuario> getAll();

    Usuario save(Usuario usuario);

    Usuario findByEmail(String email);

}
