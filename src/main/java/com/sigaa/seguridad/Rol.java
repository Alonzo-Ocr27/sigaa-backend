package com.sigaa.seguridad;

import jakarta.persistence.*;

@Entity
@Table(name = "seg_roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre; // ADMIN, CHECKIN, GATES, COMERCIAL, etc.

    public Rol() {}

    public Rol(String nombre) {
        this.nombre = nombre;
    }

    // GETTERS
    public Long getId() { return id; }
    public String getNombre() { return nombre; }

    // SETTERS
    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}