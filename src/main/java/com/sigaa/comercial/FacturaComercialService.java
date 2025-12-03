package com.sigaa.comercial;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FacturaComercialService {

    private final FacturaComercialRepository facturaRepo;
    private final ContratoComercialRepository contratoRepo;

    public FacturaComercialService(FacturaComercialRepository facturaRepo,
                                   ContratoComercialRepository contratoRepo) {
        this.facturaRepo = facturaRepo;
        this.contratoRepo = contratoRepo;
    }

    // ================================
    // GENERAR FACTURA
    // ================================
    public FacturaComercial generarFactura(Long contratoId, LocalDate fecha) {

        ContratoComercial c = contratoRepo.findById(contratoId)
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado"));

        FacturaComercial f = new FacturaComercial();
        f.setContratoId(contratoId);
        f.setFechaGenerada(fecha);
        f.setFechaVencimiento(fecha.plusDays(30));
        f.setMonto(c.getMontoMensual());
        f.setPagada(false);
        f.setEstado("AL_DIA");
        f.setMontoTotal(c.getMontoMensual());
        f.setRecargoMora(0.0);

        return facturaRepo.save(f);
    }

    // ================================
    // CALCULAR PENALIZACIÓN AUTOMÁTICA
    // ================================
    private FacturaComercial aplicarPenalizacion(FacturaComercial f) {

        if (f.isPagada()) {
            f.setEstado("AL_DIA");
            f.setRecargoMora(0.0);
            f.setMontoTotal(f.getMonto());
            return f;
        }

        LocalDate hoy = LocalDate.now();

        // No vencida
        if (!hoy.isAfter(f.getFechaVencimiento())) {
            f.setEstado("AL_DIA");
            f.setRecargoMora(0.0);
            f.setMontoTotal(f.getMonto());
            return f;
        }

        // Calcular días de atraso
        long diasAtraso = ChronoUnit.DAYS.between(f.getFechaVencimiento(), hoy);

        // 0.166% diario (IATA estándar)
        double recargo = diasAtraso * f.getMonto() * 0.00166;

        f.setRecargoMora(recargo);
        f.setMontoTotal(f.getMonto() + recargo);
        f.setEstado(diasAtraso > 30 ? "VENCIDA" : "MORA");

        return f;
    }

    // ================================
    // LISTAR FACTURAS POR CONTRATO
    // ================================
    public List<FacturaComercial> listarPorContrato(Long contratoId) {
        List<FacturaComercial> lista = facturaRepo.findByContratoId(contratoId);
        lista.forEach(this::aplicarPenalizacion);
        return lista;
    }

    // ================================
    // LISTAR TODAS LAS PENDIENTES
    // ================================
    public List<FacturaComercial> pendientes() {
        List<FacturaComercial> lista = facturaRepo.findByPagada(false);
        lista.forEach(this::aplicarPenalizacion);
        return lista;
    }

    // ================================
    // PAGAR FACTURA
    // ================================
    public FacturaComercial marcarPagada(Long id) {

        FacturaComercial f = facturaRepo.findById(id)
                .orElse(null);

        if (f == null) return null;

        // Recalcular penalización justo antes de pagar
        aplicarPenalizacion(f);

        f.setPagada(true);
        f.setEstado("AL_DIA");
        f.setRecargoMora(0.0);
        f.setMontoTotal(f.getMonto());

        return facturaRepo.save(f);
    }
}