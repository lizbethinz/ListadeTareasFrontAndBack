package com.example.MicroservicioTareas.entity;

public class LoginResponse {

    private String jwt;
    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    private Long userId; 
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private String username;
    
    public String getUsername() {
        return username;
    }

    public String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LoginResponse(String jwt, Long userId, String username, String nombre) {
        this.jwt = jwt;
        this.userId = userId;
        this.username = username;
        this.nombre = nombre;
    }

}
