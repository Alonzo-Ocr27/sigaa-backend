package com.sigaa.config;

import jakarta.persistence.*;

@Entity
@Table(name = "cfg_destinos")
public class CatalogoDestino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ciudad;
    private String pais;
    private String iata; // código aeropuerto destino

    public Long getId() { return id; }
    public String getCiudad() { return ciudad; }
    public String getPais() { return pais; }
    public String getIata() { return iata; }

    public void setId(Long id) { this.id = id; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public void setPais(String pais) { this.pais = pais; }
    public void setIata(String iata) { this.iata = iata; }
}