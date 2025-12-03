package com.sigaa.seguridad;

import jakarta.persistence.*;

@Entity
@Table(name = "seg_roles_permisos")
public class RolPermiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Rol rol;

    @ManyToOne
    private Permiso permiso;

    public RolPermiso() {}

    public Long getId() { return id; }
    public Rol getRol() { return rol; }
    public Permiso getPermiso() { return permiso; }

    public void setId(Long id) { this.id = id; }
    public void setRol(Rol rol) { this.rol = rol; }
    public void setPermiso(Permiso permiso) { this.permiso = permiso; }
}