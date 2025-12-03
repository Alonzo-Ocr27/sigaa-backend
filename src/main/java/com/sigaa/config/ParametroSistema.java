package com.sigaa.config;

import jakarta.persistence.*;

@Entity
@Table(name = "cfg_parametros")
public class ParametroSistema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String clave;

    @Column(nullable = false, length = 500)
    private String valor;

    @Column(length = 500)
    private String descripcion;

    public Long getId() { return id; }
    public String getClave() { return clave; }
    public String getValor() { return valor; }
    public String getDescripcion() { return descripcion; }

    public void setId(Long id) { this.id = id; }
    public void setClave(String clave) { this.clave = clave != null ? clave.trim() : null; }
    public void setValor(String valor) { this.valor = valor != null ? valor.trim() : null; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}