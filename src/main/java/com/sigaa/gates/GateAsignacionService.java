package com.sigaa.gates;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GateAsignacionService {

    private final GateRepository gateRepo;
    private final GateAsignacionRepository asigRepo;

    public GateAsignacionService(GateRepository gateRepo, GateAsignacionRepository asigRepo) {
        this.gateRepo = gateRepo;
        this.asigRepo = asigRepo;
    }

    // ============================================================
    // ASIGNAR GATE A UN VUELO
    // ============================================================
    public GateAsignacion asignar(Long vueloId, String tipoAeronave) {

        // Validar si el vuelo YA tiene una gate asignada activa
        GateAsignacion activa = asigRepo.findByVueloIdAndEstado(vueloId, "ACTIVA");
        if (activa != null) {
            return activa; // Ya tiene gate asignada
        }

        String tipoUpper = tipoAeronave.toUpperCase();

        // Buscar gates disponibles
        List<Gate> gates = gateRepo.findAll();

        for (Gate g : gates) {

            // Gate debe estar LIBRE
            if (!"LIBRE".equals(g.getEstado())) continue;

            // Comparación robusta de compatibilidad
            boolean compatible = g.getTiposAeronaveAceptados()
                    .stream()
                    .map(String::toUpperCase)
                    .anyMatch(t -> t.equals(tipoUpper));

            if (!compatible) continue;

            // === Asignación ===
            g.setEstado("OCUPADA");
            gateRepo.save(g);

            GateAsignacion asign = new GateAsignacion();
            asign.setGate(g);
            asign.setVueloId(vueloId);
            asign.setInicio(LocalDateTime.now());
            asign.setEstado("ACTIVA");

            return asigRepo.save(asign);
        }

        return null; // No hay gate disponible
    }

    // ============================================================
    // FINALIZAR ASIGNACIÓN
    // ============================================================
    public boolean finalizar(Long asignId) {
        GateAsignacion asign = asigRepo.findById(asignId).orElse(null);
        if (asign == null) return false;

        asign.setFin(LocalDateTime.now());
        asign.setEstado("FINALIZADA");

        Gate g = asign.getGate();
        g.setEstado("LIBRE");

        gateRepo.save(g);
        asigRepo.save(asign);

        return true;
    }

    // ============================================================
    // OBTENER ASIGNACIONES DE UN VUELO
    // ============================================================
    public List<GateAsignacion> porVuelo(Long vueloId) {
        return asigRepo.findByVueloId(vueloId);
    }
}