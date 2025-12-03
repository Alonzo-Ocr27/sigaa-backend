package com.sigaa.reportes;

public class ReporteCheckinDTO {

    private final String vuelo;
    private final long registrados;
    private final long sinCheckin;

    public ReporteCheckinDTO(String vuelo, long registrados, long sinCheckin) {
        this.vuelo = vuelo;
        this.registrados = registrados;
        this.sinCheckin = sinCheckin;
    }

    public String getVuelo() { return vuelo; }
    public long getRegistrados() { return registrados; }
    public long getSinCheckin() { return sinCheckin; }
}