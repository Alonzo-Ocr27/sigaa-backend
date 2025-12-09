package com.sigaa.auth;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String usuario;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String rol;

    public Usuario() {}

    public Usuario(String nombre, String usuario, String password, String rol) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }

    // GETTERS
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getUsuario() { return usuario; }
    public String getPassword() { return password; }
    public String getRol() { return rol; }

    // SETTERS
    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public void setPassword(String password) { this.password = password; }
    public void setRol(String rol) { this.rol = rol; }
}