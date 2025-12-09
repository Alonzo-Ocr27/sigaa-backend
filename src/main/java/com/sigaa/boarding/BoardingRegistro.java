package com.sigaa.boarding;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "boarding_registros")
public class BoardingRegistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vuelo_id", nullable = false)
    private Long vueloId;

    @Column(name = "codigo_boarding_pass", nullable = false)
    private String codigoBoardingPass;

    @Column(nullable = false)
    private String estado; // PENDIENTE, VALIDADO, DUPLICADO, CANCELADO

    @Column(name = "fecha_hora_escaneo")
    private LocalDateTime fechaHoraEscaneo;

    public BoardingRegistro() {}

    // ===== GETTERS =====
    public Long getId() { return id; }
    public Long getVueloId() { return vueloId; }
    public String getCodigoBoardingPass() { return codigoBoardingPass; }
    public String getEstado() { return estado; }
    public LocalDateTime getFechaHoraEscaneo() { return fechaHoraEscaneo; }

    // ===== SETTERS =====
    public void setId(Long id) { this.id = id; }
    public void setVueloId(Long vueloId) { this.vueloId = vueloId; }
    public void setCodigoBoardingPass(String codigoBoardingPass) { this.codigoBoardingPass = codigoBoardingPass; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setFechaHoraEscaneo(LocalDateTime fechaHoraEscaneo) { this.fechaHoraEscaneo = fechaHoraEscaneo; }
}