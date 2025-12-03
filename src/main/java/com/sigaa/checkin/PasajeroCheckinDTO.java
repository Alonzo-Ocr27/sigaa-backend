package com.sigaa.checkin;

import java.time.LocalDateTime;

public class PasajeroCheckinDTO {

    private Long id;
    private Long vueloId;

    private String nombreCompleto;
    private String documento;
    private String asiento;
    private int cantidadMaletas;

    private String estado;
    private String codigoBoardingPass;

    private LocalDateTime fechaHoraCheckin;

    public PasajeroCheckinDTO() {}

    public PasajeroCheckinDTO(PasajeroCheckin p) {
        this.id = p.getId();
        this.vueloId = p.getVueloId();
        this.nombreCompleto = p.getNombreCompleto();
        this.documento = p.getDocumento();
        this.asiento = p.getAsiento();
        this.cantidadMaletas = p.getCantidadMaletas();
        this.estado = p.getEstado();
        this.codigoBoardingPass = p.getCodigoBoardingPass();
        this.fechaHoraCheckin = p.getFechaHoraCheckin();
    }

    // GETTERS & SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getVueloId() { return vueloId; }
    public void setVueloId(Long vueloId) { this.vueloId = vueloId; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getAsiento() { return asiento; }
    public void setAsiento(String asiento) { this.asiento = asiento; }

    public int getCantidadMaletas() { return cantidadMaletas; }
    public void setCantidadMaletas(int cantidadMaletas) { this.cantidadMaletas = cantidadMaletas; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCodigoBoardingPass() { return codigoBoardingPass; }
    public void setCodigoBoardingPass(String codigoBoardingPass) { this.codigoBoardingPass = codigoBoardingPass; }

    public LocalDateTime getFechaHoraCheckin() { return fechaHoraCheckin; }
    public void setFechaHoraCheckin(LocalDateTime fechaHoraCheckin) { this.fechaHoraCheckin = fechaHoraCheckin; }
}