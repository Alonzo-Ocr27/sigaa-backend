package com.sigaa.reportes;

import com.sigaa.comercial.FacturaComercialRepository;
import com.sigaa.comercial.ContratoComercialRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReporteComercialService {

    private final FacturaComercialRepository facturaRepo;
    private final ContratoComercialRepository contratoRepo;

    public ReporteComercialService(FacturaComercialRepository f, ContratoComercialRepository c) {
        this.facturaRepo = f;
        this.contratoRepo = c;
    }

    public ReporteComercialDTO resumen() {

        long contratosActivos = contratoRepo.countByEstado("ACTIVO");
        long facturasPendientes = facturaRepo.countByPagada(false);
        long facturasVencidas = facturaRepo.countByEstado("VENCIDA");

        double totalPenalizaciones = facturaRepo.findAll().stream()
                .filter(f -> !f.isPagada())
                .mapToDouble(f -> f.getMontoTotal() - f.getMonto())
                .sum();

        double ingresosMes = facturaRepo.findAll().stream()
                .filter(f -> f.isPagada()
                        && f.getFechaGenerada().getYear() == LocalDate.now().getYear()
                        && f.getFechaGenerada().getMonthValue() == LocalDate.now().getMonthValue())
                .mapToDouble(f -> f.getMontoTotal())
                .sum();

        return new ReporteComercialDTO(
                contratosActivos,
                facturasPendientes,
                facturasVencidas,
                totalPenalizaciones,
                ingresosMes
        );
    }
}