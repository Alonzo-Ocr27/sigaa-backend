package com.sigaa.reportes;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteVuelosService vuelos;
    private final ReporteCheckinService checkin;
    private final ReporteBRSService brs;
    private final ReporteComercialService comercial;

    public ReporteController(ReporteVuelosService vuelos,
                             ReporteCheckinService checkin,
                             ReporteBRSService brs,
                             ReporteComercialService comercial) {
        this.vuelos = vuelos;
        this.checkin = checkin;
        this.brs = brs;
        this.comercial = comercial;
    }

    // ============ NUEVO ENDPOINT ============
    @GetMapping("/vuelos")
    public List<ReporteVuelosDTO> listarTodos() {
        return vuelos.todos();
    }

    // ===== VUELOS EXISTENTES =====
    @GetMapping("/vuelos/hoy")
    public List<ReporteVuelosDTO> hoy() {
        return vuelos.vuelosHoy();
    }

    @GetMapping("/vuelos/estado/{estado}")
    public List<ReporteVuelosDTO> porEstado(@PathVariable String estado) {
        return vuelos.porEstado(estado);
    }

    @GetMapping("/vuelos/origen/{o}")
    public List<ReporteVuelosDTO> porOrigen(@PathVariable String o) {
        return vuelos.porOrigen(o);
    }

    @GetMapping("/vuelos/destino/{d}")
    public List<ReporteVuelosDTO> porDestino(@PathVariable String d) {
        return vuelos.porDestino(d);
    }

    @GetMapping("/vuelos/fecha/{f}")
    public List<ReporteVuelosDTO> porFecha(@PathVariable String f) {
        return vuelos.porFecha(LocalDate.parse(f));
    }

    @GetMapping("/vuelos/rango")
    public List<ReporteVuelosDTO> entreFechas(
            @RequestParam String i,
            @RequestParam String f) {
        return vuelos.entreFechas(LocalDate.parse(i), LocalDate.parse(f));
    }

    @GetMapping("/vuelos/cancelados")
    public List<ReporteVuelosDTO> cancelados() {
        return vuelos.cancelados();
    }

    @GetMapping("/vuelos/demorados")
    public long demorados() {
        return vuelos.contarDemorados();
    }

    @GetMapping("/vuelos/puntuales")
    public long puntuales() {
        return vuelos.contarPuntuales();
    }

    // ===== CHECK-IN =====
    @GetMapping("/checkin/{vueloId}")
    public ReporteCheckinDTO checkin(@PathVariable Long vueloId) {
        return checkin.resumenVuelo(vueloId);
    }

    // ===== BRS =====
    @GetMapping("/brs/{vueloId}")
    public ReporteBRSAggregadoDTO brs(@PathVariable Long vueloId) {
        return brs.resumenVuelo(vueloId);
    }

    // ===== COMERCIAL =====
    @GetMapping("/comercial")
    public ReporteComercialDTO comercial() {
        return comercial.resumen();
    }
}