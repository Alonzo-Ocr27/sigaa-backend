package com.sigaa.comercial;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "com_arrendatarios")
public class Arrendatario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("nombre_empresa")           // JSON -> Java
    @Column(name = "nombre_empresa")          // Java -> BD
    private String nombreEmpresa;

    @JsonProperty("ruc")
    @Column(name = "ruc")
    private String ruc;

    @JsonProperty("representante")
    @Column(name = "representante")
    private String representante;

    @JsonProperty("telefono")
    @Column(name = "telefono")
    private String telefono;

    @JsonProperty("email")
    @Column(name = "email")
    private String email;

    public Arrendatario() {}

    // GETTERS/SETTERS
    public Long getId() { return id; }
    public String getNombreEmpresa() { return nombreEmpresa; }
    public String getRuc() { return ruc; }
    public String getRepresentante() { return representante; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }

    public void setId(Long id) { this.id = id; }
    public void setNombreEmpresa(String nombreEmpresa) { this.nombreEmpresa = nombreEmpresa; }
    public void setRuc(String ruc) { this.ruc = ruc; }
    public void setRepresentante(String representante) { this.representante = representante; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }
}