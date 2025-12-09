package com.sigaa.config;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CatalogoAeronaveService {

    private final CatalogoAeronaveRepository repo;

    public CatalogoAeronaveService(CatalogoAeronaveRepository repo) {
        this.repo = repo;
    }

    // =====================================================
    // CREAR
    // =====================================================
    public CatalogoAeronave crear(CatalogoAeronave a) {

        if (repo.existsByCodigo(a.getCodigo())) {
            throw new RuntimeException("El código ya está registrado");
        }

        return repo.save(a);
    }

    // =====================================================
    // LISTAR
    // =====================================================
    public List<CatalogoAeronave> listar() {
        return repo.findAll();
    }

    // =====================================================
    // ACTUALIZAR
    // =====================================================
    public CatalogoAeronave actualizar(Long id, CatalogoAeronave datos) {

        CatalogoAeronave a = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Aeronave no encontrada"));

        // validación si cambia y ya existe
        if (!a.getCodigo().equals(datos.getCodigo())
                && repo.existsByCodigo(datos.getCodigo())) {
            throw new RuntimeException("El código ya existe para otra aeronave");
        }

        a.setCodigo(datos.getCodigo());
        a.setDescripcion(datos.getDescripcion());

        return repo.save(a);
    }

    // =====================================================
    // ELIMINAR
    // =====================================================
    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Aeronave no encontrada");
        }
        repo.deleteById(id);
    }
}
