package com.sigaa.config;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogoAerolineaService {

    private final CatalogoAerolineaRepository repo;

    public CatalogoAerolineaService(CatalogoAerolineaRepository repo) {
        this.repo = repo;
    }

    // ======================================================
    // CREAR
    // ======================================================
    public CatalogoAerolinea crear(CatalogoAerolinea a) {

        if (repo.existsByCodigo(a.getCodigo())) {
            throw new RuntimeException("El código ya está registrado");
        }

        return repo.save(a);
    }

    // ======================================================
    // LISTAR
    // ======================================================
    public List<CatalogoAerolinea> listar() {
        return repo.findAll();
    }

    // ======================================================
    // ACTUALIZAR
    // ======================================================
    public CatalogoAerolinea actualizar(Long id, CatalogoAerolinea datos) {

        CatalogoAerolinea a = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Aerolínea no encontrada"));

        // validar código repetido si cambia
        if (!a.getCodigo().equals(datos.getCodigo())
                && repo.existsByCodigo(datos.getCodigo())) {

            throw new RuntimeException("El código ya está registrado por otra aerolínea");
        }

        a.setCodigo(datos.getCodigo());
        a.setNombre(datos.getNombre());

        return repo.save(a);
    }

    // ======================================================
    // ELIMINAR
    // ======================================================
    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Aerolínea no encontrada");
        }
        repo.deleteById(id);
    }
}