package com.sigaa.vuelos;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vuelos")
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String origen;

    @Column(nullable = false)
    private String destino;

    @Column(nullable = false)
    private LocalDateTime fechaHoraSalida;

    @Column(nullable = false)
    private LocalDateTime fechaHoraLlegada;

    @Column(nullable = false)
    private String estado = "PROGRAMADO";   // ⚡ Valor por defecto

    private String tipoAeronave;

    public Vuelo() {}

    public long getId() { return id; }
    public String getNumero() { return numero; }
    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public LocalDateTime getFechaHoraSalida() { return fechaHoraSalida; }
    public LocalDateTime getFechaHoraLlegada() { return fechaHoraLlegada; }
    public String getEstado() { return estado; }
    public String getTipoAeronave() { return tipoAeronave; }

    public void setId(Long id) { this.id = id; }
    public void setNumero(String numero) { this.numero = numero; }
    public void setOrigen(String origen) { this.origen = origen; }
    public void setDestino(String destino) { this.destino = destino; }
    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) { this.fechaHoraSalida = fechaHoraSalida; }
    public void setFechaHoraLlegada(LocalDateTime fechaHoraLlegada) { this.fechaHoraLlegada = fechaHoraLlegada; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setTipoAeronave(String tipoAeronave) { this.tipoAeronave = tipoAeronave; }
}