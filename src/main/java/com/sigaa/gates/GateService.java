package com.sigaa.gates;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GateService {

    private final GateRepository gateRepo;

    public GateService(GateRepository gateRepo) {
        this.gateRepo = gateRepo;
    }

    public Gate crear(Gate gate) {
        if (gateRepo.existsByCodigo(gate.getCodigo())) {
            throw new RuntimeException("Ya existe una gate con ese código");
        }

        gate.setEstado("LIBRE");
        return gateRepo.save(gate);
    }

    public List<Gate> listar() {
        return gateRepo.findAll();
    }

    public Gate buscar(Long id) {
        return gateRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Gate no encontrada"));
    }

    public Gate actualizar(Long id, Gate datos) {
        Gate g = buscar(id);

        if (!g.getCodigo().equals(datos.getCodigo())
                && gateRepo.existsByCodigo(datos.getCodigo())) {
            throw new RuntimeException("Código ya existente");
        }

        g.setCodigo(datos.getCodigo());
        g.setTipo(datos.getTipo());
        g.setTiposAeronaveAceptados(datos.getTiposAeronaveAceptados());

        return gateRepo.save(g);
    }

    public void cambiarEstado(Long id, String estado) {
        Gate g = buscar(id);
        g.setEstado(estado);
        gateRepo.save(g);
    }
}