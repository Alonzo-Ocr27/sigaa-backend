package com.sigaa.config;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CatalogoAeronaveService {

    private final CatalogoAeronaveRepository repo;

    public CatalogoAeronaveService(CatalogoAeronaveRepository repo) {
        this.repo = repo;
    }

    public CatalogoAeronave crear(CatalogoAeronave a) {
        return repo.save(a);
    }

    public List<CatalogoAeronave> listar() {
        return repo.findAll();
    }

    public CatalogoAeronave actualizar(Long id, CatalogoAeronave datos) {
        CatalogoAeronave a = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Aeronave no encontrada"));

        a.setCodigo(datos.getCodigo());
        a.setDescripcion(datos.getDescripcion());

        return repo.save(a);
    }

    public boolean eliminar(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}