package com.sigaa.gates;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GateService {

    private final GateRepository gateRepo;

    public GateService(GateRepository gateRepo) {
        this.gateRepo = gateRepo;
    }

    // =============================
    // CREAR
    // =============================
    public Gate crear(Gate gate) {

        // Validar código único
        if (gateRepo.existsByCodigo(gate.getCodigo())) {
            throw new RuntimeException("Ya existe una gate con ese código");
        }

        gate.setEstado("LIBRE");
        return gateRepo.save(gate);
    }

    // =============================
    // LISTAR
    // =============================
    public List<Gate> listar() {
        return gateRepo.findAll();
    }

    // =============================
    // BUSCAR
    // =============================
    public Gate buscar(Long id) {
        return gateRepo.findById(id).orElse(null);
    }

    // =============================
    // ACTUALIZAR
    // =============================
    public Gate actualizar(Long id, Gate datos) {
        Gate g = buscar(id);
        if (g == null) return null;

        // Validar cambio de código sin duplicar
        if (!g.getCodigo().equals(datos.getCodigo())
                && gateRepo.existsByCodigo(datos.getCodigo())) {
            throw new RuntimeException("Código de gate ya existente");
        }

        g.setCodigo(datos.getCodigo());
        g.setTipo(datos.getTipo());
        g.setTiposAeronaveAceptados(datos.getTiposAeronaveAceptados());

        return gateRepo.save(g);
    }

    // =============================
    // CAMBIAR ESTADO
    // =============================
    public boolean cambiarEstado(Long id, String estado) {
        Gate g = buscar(id);
        if (g == null) return false;

        g.setEstado(estado);
        gateRepo.save(g);
        return true;
    }
}