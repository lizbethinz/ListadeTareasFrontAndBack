package com.example.MicroservicioTareas.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.MicroservicioTareas.entity.Usuario;

@Repository
public interface UserRepository extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

}
