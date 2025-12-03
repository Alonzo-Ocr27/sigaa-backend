package com.sigaa.reportes;

public class ReporteBRSAggregadoDTO {

    private final String numeroVuelo;
    private final Long totalMaletas;
    private final Long maletasExtraviadas;

    public ReporteBRSAggregadoDTO(String numeroVuelo, Long totalMaletas, Long maletasExtraviadas) {
        this.numeroVuelo = numeroVuelo;
        this.totalMaletas = totalMaletas;
        this.maletasExtraviadas = maletasExtraviadas;
    }

    public String getNumeroVuelo() { return numeroVuelo; }
    public Long getTotalMaletas() { return totalMaletas; }
    public Long getMaletasExtraviadas() { return maletasExtraviadas; }
}