package com.sigaa.seguridad;

import jakarta.persistence.*;

@Entity
@Table(name = "seg_usuarios_roles")
public class UsuarioRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId;

    @ManyToOne
    private Rol rol;

    public UsuarioRol() {}

    public Long getId() { return id; }
    public Long getUsuarioId() { return usuarioId; }
    public Rol getRol() { return rol; }

    public void setId(Long id) { this.id = id; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public void setRol(Rol rol) { this.rol = rol; }
}