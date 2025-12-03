package com.sigaa.reportes;

import com.sigaa.vuelos.Vuelo;
import com.sigaa.vuelos.VueloRepository;
import com.sigaa.comercial.FacturaComercial;
import com.sigaa.comercial.FacturaComercialRepository;
import com.sigaa.brs.Maleta;
import com.sigaa.brs.MaletaRepository;
import com.sigaa.checkin.PasajeroCheckin;
import com.sigaa.checkin.PasajeroCheckinRepository;
import com.sigaa.gates.GateRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    private final VueloRepository vueloRepo;
    private final FacturaComercialRepository facturaRepo;
    private final MaletaRepository maletaRepo;
    private final PasajeroCheckinRepository checkinRepo;
    private final GateRepository gateRepo;

    public ReporteService(VueloRepository vueloRepo,
                          FacturaComercialRepository facturaRepo,
                          MaletaRepository maletaRepo,
                          PasajeroCheckinRepository checkinRepo,
                          GateRepository gateRepo) {

        this.vueloRepo = vueloRepo;
        this.facturaRepo = facturaRepo;
        this.maletaRepo = maletaRepo;
        this.checkinRepo = checkinRepo;
        this.gateRepo = gateRepo;
    }

    // =============================
    // VUELOS
    // =============================

    public List<ReporteVuelosDTO> vuelosHoy() {
        LocalDate hoy = LocalDate.now();

        return vueloRepo.findAll().stream()
                .filter(v -> v.getFechaHoraSalida().toLocalDate().equals(hoy))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReporteVuelosDTO> vuelosPorFecha(LocalDate fecha) {
        return vueloRepo.findAll().stream()
                .filter(v -> v.getFechaHoraSalida().toLocalDate().equals(fecha))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReporteVuelosDTO> vuelosPorEstado(String estado) {
        return vueloRepo.findByEstado(estado).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public long contarDemorados() {
        return vueloRepo.countByEstado("DEMORADO");
    }

    public long contarPuntuales() {
        return vueloRepo.countByEstado("PROGRAMADO");
    }

    public List<ReporteVuelosDTO> vuelosPorRango(LocalDate inicio, LocalDate fin) {
        return vueloRepo.findAll().stream()
                .filter(v -> {
                    LocalDate f = v.getFechaHoraSalida().toLocalDate();
                    return (!f.isBefore(inicio) && !f.isAfter(fin));
                })
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ReporteVuelosDTO toDTO(Vuelo v) {
        return new ReporteVuelosDTO(
                v.getNumero(),
                v.getOrigen(),
                v.getDestino(),
                v.getFechaHoraSalida(),
                v.getFechaHoraLlegada(),
                v.getEstado(),
                v.getTipoAeronave()
        );
    }

    // =============================
    // BRS
    // =============================

    public long totalMaletas() {
        return maletaRepo.count();
    }

    public long maletasExtraviadas() {
        return maletaRepo.countByEstado("EXTRAVIADA");
    }

    public long maletasEnTransito() {
        return maletaRepo.countByEstado("TRANSFERENCIA"); // CORREGIDO
    }

    public long maletasPorVuelo(Long vueloId) {
        return maletaRepo.countByVueloId(vueloId);
    }

    public List<Maleta> maletasPorEstado(String estado) {
        return maletaRepo.findByEstado(estado);
    }

    // =============================
    // CHECK-IN
    // =============================

    public long pasajerosHoy() {
        LocalDate hoy = LocalDate.now();

        return checkinRepo.findAll().stream()
                .filter(c -> c.getFechaHoraCheckin() != null
                        && c.getFechaHoraCheckin().toLocalDate().equals(hoy))
                .count();
    }

    public List<PasajeroCheckin> pasajerosPorVuelo(Long vueloId) {
        return checkinRepo.findByVueloId(vueloId);
    }

    public long checkinsCancelados() {
        return checkinRepo.countByEstado("CANCELADO");
    }

    // =============================
    // COMERCIAL
    // =============================

    public List<FacturaComercial> facturasPendientes() {
        return facturaRepo.findByPagada(false);
    }

    public List<FacturaComercial> facturasVencidas() {
        LocalDate hoy = LocalDate.now();
        return facturaRepo.findAll().stream()
                .filter(f -> !f.isPagada() && hoy.isAfter(f.getFechaVencimiento()))
                .collect(Collectors.toList());
    }

    public double ingresosDelMes(int year, int mes) {
        return facturaRepo.findAll().stream()
                .filter(f -> f.isPagada()
                        && f.getFechaGenerada().getYear() == year
                        && f.getFechaGenerada().getMonthValue() == mes)
                .mapToDouble(FacturaComercial::getMontoTotal)
                .sum();
    }

    public double totalPenalizaciones() {
        return facturaRepo.findAll().stream()
                .mapToDouble(f -> {
                    Double recargo = f.getRecargoMora();
                    return recargo == null ? 0 : recargo;
                })
                .sum();
    }

    // =============================
    // GATES
    // =============================

    public long gatesDisponibles() {
        return gateRepo.countByEstado("LIBRE");
    }

    public long gatesOcupadas() {
        return gateRepo.countByEstado("OCUPADA"); // CORREGIDO
    }
}