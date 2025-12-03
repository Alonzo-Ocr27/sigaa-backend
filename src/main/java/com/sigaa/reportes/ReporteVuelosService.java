package com.sigaa.reportes;

import com.sigaa.vuelos.VueloRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReporteVuelosService {

    private final VueloRepository repo;

    public ReporteVuelosService(VueloRepository repo) {
        this.repo = repo;
    }

    public List<ReporteVuelosDTO> todos() {
        return repo.findAllVuelosReporte();
    }

    public List<ReporteVuelosDTO> vuelosHoy() {
        return List.of(); // si ya tienes implementación, la agregas
    }

    public List<ReporteVuelosDTO> porEstado(String estado) {
        return List.of(); // placeholder
    }

    public List<ReporteVuelosDTO> porOrigen(String origen) {
        return List.of(); // placeholder
    }

    public List<ReporteVuelosDTO> porDestino(String destino) {
        return List.of(); // placeholder
    }

    public List<ReporteVuelosDTO> porFecha(java.time.LocalDate fecha) {
        return List.of(); // placeholder
    }

    public List<ReporteVuelosDTO> entreFechas(java.time.LocalDate i, java.time.LocalDate f) {
        return List.of(); // placeholder
    }

    public List<ReporteVuelosDTO> cancelados() {
        return List.of(); // placeholder
    }

    public long contarDemorados() {
        return 0; // placeholder
    }

    public long contarPuntuales() {
        return 0; // placeholder
    }
}
