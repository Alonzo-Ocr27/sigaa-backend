package com.sigaa.auth;

import java.util.List;

public class AuthResponse {

    private String token;
    private Long idUsuario;
    private String nombre;
    private String usuario;
    private List<String> roles;

    public AuthResponse(String token, Long idUsuario, String nombre, String usuario, List<String> roles) {
        this.token = token;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.usuario = usuario;
        this.roles = roles;
    }

    public String getToken() { return token; }
    public Long getIdUsuario() { return idUsuario; }
    public String getNombre() { return nombre; }
    public String getUsuario() { return usuario; }
    public List<String> getRoles() { return roles; }
}