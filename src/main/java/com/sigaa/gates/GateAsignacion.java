package com.sigaa.gates;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gate_asignaciones")
public class GateAsignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long vueloId; // relación con módulo vuelos

    @ManyToOne
    @JoinColumn(name = "gate_id", nullable = false)
    private Gate gate;

    private LocalDateTime inicio;
    private LocalDateTime fin;

    @Column(nullable = false)
    private String estado; // ACTIVA, FINALIZADA, CANCELADA

    public GateAsignacion() {}

    // === GETTERS ===
    public Long getId() { return id; }
    public Long getVueloId() { return vueloId; }
    public Gate getGate() { return gate; }
    public LocalDateTime getInicio() { return inicio; }
    public LocalDateTime getFin() { return fin; }
    public String getEstado() { return estado; }

    // === SETTERS ===
    public void setId(Long id) { this.id = id; }             // <--- IMPORTANTE
    public void setVueloId(Long vueloId) { this.vueloId = vueloId; }
    public void setGate(Gate gate) { this.gate = gate; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }
    public void setFin(LocalDateTime fin) { this.fin = fin; }
    public void setEstado(String estado) { this.estado = estado; }
}