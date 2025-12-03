package com.sigaa.notificaciones;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Puede ser null si es notificación general
    @Column(nullable = true)
    private Long usuarioId;

    @Column(nullable = false)
    private String canal = "SISTEMA"; // EMAIL, SMS, PUSH, SISTEMA

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String mensaje;

    @Column(nullable = false)
    private String estado = "PENDIENTE"; // PENDIENTE, ENVIADA, ERROR

    private String errorDetalle;

    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private LocalDateTime fechaEnvio;

    public Notificacion() {}

    // GETTERS
    public Long getId() { return id; }
    public Long getUsuarioId() { return usuarioId; }
    public String getCanal() { return canal; }
    public String getTitulo() { return titulo; }
    public String getMensaje() { return mensaje; }
    public String getEstado() { return estado; }
    public String getErrorDetalle() { return errorDetalle; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }

    // SETTERS
    public void setId(Long id) { this.id = id; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public void setCanal(String canal) { this.canal = canal; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setErrorDetalle(String errorDetalle) { this.errorDetalle = errorDetalle; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }
}