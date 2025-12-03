package com.sigaa.reportes;

public class ReporteComercialDTO {

    private long contratosActivos;
    private long facturasPendientes;
    private long facturasVencidas;
    private double totalPenalizaciones;
    private double ingresosMes;

    public ReporteComercialDTO(long contratosActivos, long facturasPendientes,
                               long facturasVencidas, double totalPenalizaciones,
                               double ingresosMes) {
        this.contratosActivos = contratosActivos;
        this.facturasPendientes = facturasPendientes;
        this.facturasVencidas = facturasVencidas;
        this.totalPenalizaciones = totalPenalizaciones;
        this.ingresosMes = ingresosMes;
    }

    public long getContratosActivos() { return contratosActivos; }
    public long getFacturasPendientes() { return facturasPendientes; }
    public long getFacturasVencidas() { return facturasVencidas; }
    public double getTotalPenalizaciones() { return totalPenalizaciones; }
    public double getIngresosMes() { return ingresosMes; }
}