package com.example.MicroservicioTareas.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.MicroservicioTareas.entity.Usuario;
import com.example.MicroservicioTareas.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioService {
     @Autowired
        UserRepository repository;

        @Autowired
        private PasswordEncoder passwordEncoder;


    @Transactional
    public Usuario save(Usuario usuario) throws IllegalStateException{
        if (repository.existsByEmail(usuario.getEmail())) {
            throw new IllegalStateException("El email ya está registrado.");
        }
       

    String encodedPassword = passwordEncoder.encode(usuario.getPassword());
      usuario.setPassword(encodedPassword);

        return repository.save(usuario);
    }


    @Override
    public List<Usuario> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }


    @Override
    public Usuario findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }


    public Usuario findById(Long idUsuario) {
        return repository.findById(idUsuario).orElse(null);
    }

}
