package com.sigaa.checkin;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "checkin_pasajeros")
public class PasajeroCheckin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vuelo_id", nullable = false)
    private Long vueloId;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @Column(nullable = false)
    private String documento;

    @Column(nullable = false)
    private String asiento;

    @Column(name = "cantidad_maletas", nullable = false)
    private int cantidadMaletas;

    @Column(nullable = false)
    private String estado; // PENDIENTE, COMPLETADO, CANCELADO

    @Column(name = "codigo_boarding_pass", nullable = false, unique = true)
    private String codigoBoardingPass;

    @Column(name = "fecha_hora_checkin")
    private LocalDateTime fechaHoraCheckin;

    // ============================
    // GETTERS
    // ============================
    public Long getId() { return id; }
    public Long getVueloId() { return vueloId; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getDocumento() { return documento; }
    public String getAsiento() { return asiento; }
    public int getCantidadMaletas() { return cantidadMaletas; }
    public String getEstado() { return estado; }
    public String getCodigoBoardingPass() { return codigoBoardingPass; }
    public LocalDateTime getFechaHoraCheckin() { return fechaHoraCheckin; }

    // ============================
    // SETTERS
    // ============================
    public void setId(Long id) { this.id = id; }
    public void setVueloId(Long vueloId) { this.vueloId = vueloId; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public void setDocumento(String documento) { this.documento = documento; }
    public void setAsiento(String asiento) { this.asiento = asiento; }
    public void setCantidadMaletas(int cantidadMaletas) { this.cantidadMaletas = cantidadMaletas; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setCodigoBoardingPass(String codigoBoardingPass) { this.codigoBoardingPass = codigoBoardingPass; }
    public void setFechaHoraCheckin(LocalDateTime fechaHoraCheckin) { this.fechaHoraCheckin = fechaHoraCheckin; }
}