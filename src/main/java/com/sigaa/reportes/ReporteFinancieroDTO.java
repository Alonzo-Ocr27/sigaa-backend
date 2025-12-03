package com.sigaa.reportes;

import java.time.LocalDate;

public class ReporteFinancieroDTO {

    private final Long facturaId;
    private final String local;
    private final double monto;
    private final double recargo;
    private final double total;
    private final boolean pagada;
    private final String estado;
    private final LocalDate fechaGenerada;
    private final LocalDate fechaVencimiento;

    public ReporteFinancieroDTO(Long facturaId, String local, double monto, double recargo,
                                double total, boolean pagada, String estado,
                                LocalDate generada, LocalDate vencimiento) {

        this.facturaId = facturaId;
        this.local = local;
        this.monto = monto;
        this.recargo = recargo;
        this.total = total;
        this.pagada = pagada;
        this.estado = estado;
        this.fechaGenerada = generada;
        this.fechaVencimiento = vencimiento;
    }

    // Constructor opcional sin recargos (si lo necesitas)
    public ReporteFinancieroDTO(Long facturaId, String local, double monto,
                                boolean pagada, String estado,
                                LocalDate generada, LocalDate vencimiento) {

        this(facturaId, local, monto, 0.0, monto, pagada, estado, generada, vencimiento);
    }

    public Long getFacturaId() { return facturaId; }
    public String getLocal() { return local; }
    public double getMonto() { return monto; }
    public double getRecargo() { return recargo; }
    public double getTotal() { return total; }
    public boolean isPagada() { return pagada; }
    public String getEstado() { return estado; }
    public LocalDate getFechaGenerada() { return fechaGenerada; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
}