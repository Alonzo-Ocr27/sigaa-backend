package com.sigaa.seguridad;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "seg_auditoria",
    indexes = {
        @Index(name = "idx_usuario", columnList = "usuarioId"),
        @Index(name = "idx_modulo", columnList = "modulo"),
        @Index(name = "idx_fecha", columnList = "fechaHora")
    }
)
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    @Column(length = 20)
    private String modulo;

    @Column(length = 10)
    private String accion;

    @Column(length = 300)
    private String detalle;

    @Column(length = 40)
    private String ip;

    private LocalDateTime fechaHora;

    public Auditoria() {}

    public Long getId() { return id; }
    public Long getUsuarioId() { return usuarioId; }
    public String getModulo() { return modulo; }
    public String getAccion() { return accion; }
    public String getDetalle() { return detalle; }
    public String getIp() { return ip; }
    public LocalDateTime getFechaHora() { return fechaHora; }

    public void setId(Long id) { this.id = id; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public void setModulo(String modulo) { this.modulo = modulo; }
    public void setAccion(String accion) { this.accion = accion; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
    public void setIp(String ip) { this.ip = ip; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
}