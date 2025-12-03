package com.sigaa.brs;

import jakarta.persistence.*;

@Entity
@Table(name = "brs_maletas")
public class Maleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación lógica con vuelo (módulo vuelos)
    @Column(nullable = false)
    private Long vueloId;

    // Relación lógica con check-in (puede ser null si es carga, por ejemplo)
    private Long pasajeroCheckinId;

    @Column(nullable = false, unique = true)
    private String tagCode;   // código de etiqueta (IATA, etc.)

    @Column(nullable = false)
    private double pesoKg;

    @Column(nullable = false)
    private String estado;    // REGISTRADA, EN_BHS, CARGADA, DESCARGADA, EXTRAVIADA, TRANSFERENCIA

    private String destinoFinal; // IATA ej: PTY, MIA
    private String origen;       // aeropuerto origen
    private String ubicacionActual; // cinta, hold, bodega, almacén, etc.

    public Maleta() {}

    // GETTERS
    public Long getId() { return id; }
    public Long getVueloId() { return vueloId; }
    public Long getPasajeroCheckinId() { return pasajeroCheckinId; }
    public String getTagCode() { return tagCode; }
    public double getPesoKg() { return pesoKg; }
    public String getEstado() { return estado; }
    public String getDestinoFinal() { return destinoFinal; }
    public String getOrigen() { return origen; }
    public String getUbicacionActual() { return ubicacionActual; }

    // SETTERS
    public void setId(Long id) { this.id = id; }
    public void setVueloId(Long vueloId) { this.vueloId = vueloId; }
    public void setPasajeroCheckinId(Long pasajeroCheckinId) { this.pasajeroCheckinId = pasajeroCheckinId; }
    public void setTagCode(String tagCode) { this.tagCode = tagCode; }
    public void setPesoKg(double pesoKg) { this.pesoKg = pesoKg; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setDestinoFinal(String destinoFinal) { this.destinoFinal = destinoFinal; }
    public void setOrigen(String origen) { this.origen = origen; }
    public void setUbicacionActual(String ubicacionActual) { this.ubicacionActual = ubicacionActual; }
}