package com.example.MicroservicioTareas.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;


import org.springframework.web.filter.OncePerRequestFilter;

import com.example.MicroservicioTareas.util.JwtUtil;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtUtil jwtService; // CLASE QUE MANEJA LA LECTURA/VALIDACIÓN DEL JWT
    private final UserDetailsService userDetailsService; // NECESARIO PARA CARGAR EL USUARIO

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username; // o email, dependiendo de lo que uses como SUB en el token

        // 1. Verificar si hay un token válido
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Si no hay token o no tiene el prefijo, pasa al siguiente filtro (y el AuthorizationFilter lo bloqueará si la ruta está protegida)
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extraer el token y el usuario
        jwt = authHeader.substring(7); // "Bearer " tiene 7 caracteres
        
        // **IMPORTANTE:** Aquí DEBES usar tu JwtService para extraer el nombre de usuario
        username = jwtService.extractUsername(jwt); 

        // 3. Validar el token y autenticar
        // Si el username no es nulo y AÚN NO hay autenticación en el SecurityContext
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // Cargar los detalles del usuario desde la base de datos o servicio
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Validar si el token es válido (no expirado, firma correcta)
            if (jwtService.validateToken(jwt, userDetails)) {
                
                // Si es válido, crear el objeto de autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, // Las credenciales son nulas en JWT
                    userDetails.getAuthorities() // Roles/Autoridades
                );

                // Asignar detalles de la solicitud (IP, Session ID, etc.)
                authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Establecer la autenticación en el SecurityContext (¡Esto es lo que resuelve el 401!)
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }

}
