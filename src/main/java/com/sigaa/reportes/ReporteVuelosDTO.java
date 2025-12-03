package com.sigaa.reportes;

import java.time.LocalDateTime;

public class ReporteVuelosDTO {

    private final String numero;
    private final String origen;
    private final String destino;
    private final LocalDateTime salida;
    private final LocalDateTime llegada;
    private final String estado;
    private final String tipoAeronave;

    public ReporteVuelosDTO(String numero,
                            String origen,
                            String destino,
                            LocalDateTime salida,
                            LocalDateTime llegada,
                            String estado,
                            String tipoAeronave) {
        this.numero = numero;
        this.origen = origen;
        this.destino = destino;
        this.salida = salida;
        this.llegada = llegada;
        this.estado = estado;
        this.tipoAeronave = tipoAeronave;
    }

    public String getNumero() { return numero; }
    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public LocalDateTime getSalida() { return salida; }
    public LocalDateTime getLlegada() { return llegada; }
    public String getEstado() { return estado; }
    public String getTipoAeronave() { return tipoAeronave; }
}