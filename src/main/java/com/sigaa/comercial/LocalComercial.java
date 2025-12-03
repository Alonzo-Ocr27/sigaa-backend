package com.sigaa.comercial;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "com_locales")
public class LocalComercial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("codigo")
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @JsonProperty("metros_cuadrados")
    @Column(name = "metros_cuadrados", nullable = false)
    private double metrosCuadrados;

    @JsonProperty("ubicacion")
    @Column(name = "ubicacion", nullable = false)
    private String ubicacion;

    @JsonProperty("precio_base")
    @Column(name = "precio_base", nullable = false)
    private double precioBase;

    @JsonProperty("estado")
    @Column(name = "estado", nullable = false)
    private String estado;

    public LocalComercial() {}

    // GETTERS y SETTERS
    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public double getMetrosCuadrados() { return metrosCuadrados; }
    public String getUbicacion() { return ubicacion; }
    public double getPrecioBase() { return precioBase; }
    public String getEstado() { return estado; }

    public void setId(Long id) { this.id = id; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setMetrosCuadrados(double metrosCuadrados) { this.metrosCuadrados = metrosCuadrados; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public void setPrecioBase(double precioBase) { this.precioBase = precioBase; }
    public void setEstado(String estado) { this.estado = estado; }
}