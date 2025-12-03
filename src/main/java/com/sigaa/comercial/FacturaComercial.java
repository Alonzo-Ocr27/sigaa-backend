package com.sigaa.comercial;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "com_facturas")
public class FacturaComercial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long contratoId;

    private LocalDate fechaGenerada;
    private LocalDate fechaVencimiento;

    @Column(nullable = false)
    private double monto;

    @Column(nullable = false)
    private boolean pagada;

    @Column(nullable = false)
    private double recargoMora = 0.0;

    @Column(nullable = false)
    private double montoTotal = 0.0;

    @Column(nullable = false)
    private String estado = "AL_DIA";

    public FacturaComercial() {}

    // GETTERS
    public Long getId() { return id; }
    public Long getContratoId() { return contratoId; }
    public LocalDate getFechaGenerada() { return fechaGenerada; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public double getMonto() { return monto; }
    public boolean isPagada() { return pagada; }
    public double getRecargoMora() { return recargoMora; }
    public double getMontoTotal() { return montoTotal; }
    public String getEstado() { return estado; }

    // SETTERS
    public void setId(Long id) { this.id = id; }
    public void setContratoId(Long contratoId) { this.contratoId = contratoId; }
    public void setFechaGenerada(LocalDate fechaGenerada) { this.fechaGenerada = fechaGenerada; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    public void setMonto(double monto) { this.monto = monto; }
    public void setPagada(boolean pagada) { this.pagada = pagada; }
    public void setRecargoMora(double recargoMora) { this.recargoMora = recargoMora; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }
    public void setEstado(String estado) { this.estado = estado; }
}