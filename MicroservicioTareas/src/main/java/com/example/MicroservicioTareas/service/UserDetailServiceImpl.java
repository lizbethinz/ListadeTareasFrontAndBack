package com.example.MicroservicioTareas.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.MicroservicioTareas.entity.SecurityUser;
import com.example.MicroservicioTareas.entity.Usuario;
import com.example.MicroservicioTareas.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
    Usuario usuario = userRepository.findByEmail(username)
              .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + username));

    return new SecurityUser(usuario);
    }

}
