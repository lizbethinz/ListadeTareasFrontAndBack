package com.example.MicroservicioTareas.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.MicroservicioTareas.entity.Usuario;
public class CustomUserDetails implements UserDetails{

    private final Usuario usuario;

    // Constructor que acepta tu entidad Usuario
    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    // ----------------------------------------------------
    // MÉTODOS REQUERIDOS POR SPRING SECURITY
    // ----------------------------------------------------

    // 1. Obtener Roles/Autoridades
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // En este ejemplo simple, asignamos el rol "USER" por defecto
        // Si tu entidad Usuario tiene un campo de roles, se mapearía aquí.
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // 2. Obtener la Contraseña (¡Debe ser el HASH cifrado de la BD!)
    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    // 3. Obtener el Username (¡En tu caso, el EMAIL!)
    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    // 4. Métodos de Estado (normalmente true por defecto)
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
