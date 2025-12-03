package com.sigaa.comercial;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "com_contratos")
public class ContratoComercial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "local_id")
    private LocalComercial local;

    @ManyToOne
    @JoinColumn(name = "arrendatario_id")
    private Arrendatario arrendatario;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private double montoMensual; // cálculo automático
    private String estado; // ACTIVO, FINALIZADO, CANCELADO

    public ContratoComercial() {}

    // GETTERS/SETTERS
    public Long getId() { return id; }
    public LocalComercial getLocal() { return local; }
    public Arrendatario getArrendatario() { return arrendatario; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public double getMontoMensual() { return montoMensual; }
    public String getEstado() { return estado; }

    public void setId(Long id) { this.id = id; }
    public void setLocal(LocalComercial local) { this.local = local; }
    public void setArrendatario(Arrendatario arrendatario) { this.arrendatario = arrendatario; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public void setMontoMensual(double montoMensual) { this.montoMensual = montoMensual; }
    public void setEstado(String estado) { this.estado = estado; }
}