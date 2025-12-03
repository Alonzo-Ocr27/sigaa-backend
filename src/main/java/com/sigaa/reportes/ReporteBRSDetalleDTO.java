package com.sigaa.reportes;

public class ReporteBRSDetalleDTO {

    private final Long maletaId;
    private final String vuelo;
    private final String pasajero;
    private final String estado;
    private final Double peso;

    public ReporteBRSDetalleDTO(Long maletaId, String vuelo, String pasajero,
                                String estado, Double peso) {
        this.maletaId = maletaId;
        this.vuelo = vuelo;
        this.pasajero = pasajero;
        this.estado = estado;
        this.peso = peso;
    }

    public Long getMaletaId() { return maletaId; }
    public String getVuelo() { return vuelo; }
    public String getPasajero() { return pasajero; }
    public String getEstado() { return estado; }
    public Double getPeso() { return peso; }
}
