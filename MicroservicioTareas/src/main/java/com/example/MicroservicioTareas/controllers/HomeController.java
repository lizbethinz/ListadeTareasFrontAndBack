package com.example.MicroservicioTareas.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MicroservicioTareas.entity.LoginRequest;
import com.example.MicroservicioTareas.entity.LoginResponse;
import com.example.MicroservicioTareas.entity.Usuario;
import com.example.MicroservicioTareas.service.IUsuarioService;
import com.example.MicroservicioTareas.util.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/v1")
@EnableMethodSecurity
@RequiredArgsConstructor
public class HomeController {

       private final IUsuarioService service;

       @Autowired
        @Qualifier("userDetailServiceImpl")
        private UserDetailsService userDetailsService;

       @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private JwtUtil jwtUtil;

    @GetMapping("/home")
    public String home() {
        return "Private home";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAutoritiy('ADMIN')")
    public String admin(){
        return "Admin home";
    }

    @PostMapping("/register")
     public ResponseEntity<?> save(@RequestBody Usuario usuario){

        try {
        Usuario usuarioRegistrado = service.save(usuario);
            // Devolvemos CREATED (201) si todo va bien.
            return new ResponseEntity<>(usuarioRegistrado.getEmail() + " registrado exitosamente.", HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // 409
        } catch (Exception e) {
            // Esto captura ERRORES de BD si no son manejados por Spring
            return new ResponseEntity<>("Error interno al registrar usuario.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
     }

    @PostMapping("/login") 
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) {
    try {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), 
                loginRequest.getPassword()
            )
        );
    } catch (BadCredentialsException e) {
               return new ResponseEntity<>("Credenciales inválidas (email o contraseña incorrectos).", HttpStatus.UNAUTHORIZED);
    }
    final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail()); 

    final String jwt = jwtUtil.generateToken(userDetails);

    Usuario usuario = service.findByEmail(loginRequest.getEmail());

    LoginResponse response = new LoginResponse(
            jwt, 
            usuario.getId(), 
            usuario.getEmail(),
            usuario.getNombre()
        );
    
    return ResponseEntity.ok(response);
    }
    
    
}
