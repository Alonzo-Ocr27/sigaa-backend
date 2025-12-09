package com.sigaa.brs;

import jakarta.persistence.*;

@Entity
@Table(name = "brs_maletas")
public class Maleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vuelo_id", nullable = false)
    private Long vueloId;

    @Column(name = "pasajero_checkin_id")
    private Long pasajeroCheckinId;

    @Column(name = "tag_code", nullable = false, unique = true)
    private String tagCode;

    @Column(name = "peso_kg", nullable = false)
    private double pesoKg;

    @Column(nullable = false)
    private String estado;

    @Column(name = "destino_final")
    private String destinoFinal;

    @Column(name = "origen")
    private String origen;

    @Column(name = "ubicacion_actual")
    private String ubicacionActual;

    public Maleta() {}

    // ========================
    // GETTERS
    // ========================
    public Long getId() { return id; }
    public Long getVueloId() { return vueloId; }
    public Long getPasajeroCheckinId() { return pasajeroCheckinId; }
    public String getTagCode() { return tagCode; }
    public double getPesoKg() { return pesoKg; }
    public String getEstado() { return estado; }
    public String getDestinoFinal() { return destinoFinal; }
    public String getOrigen() { return origen; }
    public String getUbicacionActual() { return ubicacionActual; }

    // ========================
    // SETTERS
    // ========================
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