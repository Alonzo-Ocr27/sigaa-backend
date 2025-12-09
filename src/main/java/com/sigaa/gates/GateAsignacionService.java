package com.sigaa.gates;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GateAsignacionService {

    private final GateRepository gateRepo;
    private final GateAsignacionRepository asigRepo;

    public GateAsignacionService(GateRepository gateRepo,
                                 GateAsignacionRepository asigRepo) {

        this.gateRepo = gateRepo;
        this.asigRepo = asigRepo;
    }

    public GateAsignacion asignar(Long vueloId, String tipoAeronave) {

        GateAsignacion activa = asigRepo.findByVueloIdAndEstado(vueloId, "ACTIVA");
        if (activa != null) return activa;

        String tipoUpper = tipoAeronave.toUpperCase();

        for (Gate g : gateRepo.findAll()) {

            if (!"LIBRE".equalsIgnoreCase(g.getEstado())) continue;

            boolean compatible = g.getTiposAeronaveAceptados()
                    .stream()
                    .map(String::toUpperCase)
                    .anyMatch(t -> t.equals(tipoUpper));

            if (!compatible) continue;

            g.setEstado("OCUPADA");
            gateRepo.save(g);

            GateAsignacion asign = new GateAsignacion();
            asign.setGate(g);
            asign.setVueloId(vueloId);
            asign.setInicio(LocalDateTime.now());
            asign.setEstado("ACTIVA");

            return asigRepo.save(asign);
        }

        return null;
    }

    public void finalizar(Long asignId) {

        GateAsignacion asign = asigRepo.findById(asignId)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        asign.setEstado("FINALIZADA");
        asign.setFin(LocalDateTime.now());

        Gate g = asign.getGate();
        g.setEstado("LIBRE");

        gateRepo.save(g);
        asigRepo.save(asign);
    }

    public List<GateAsignacion> porVuelo(Long vueloId) {
        return asigRepo.findByVueloId(vueloId);
    }
}