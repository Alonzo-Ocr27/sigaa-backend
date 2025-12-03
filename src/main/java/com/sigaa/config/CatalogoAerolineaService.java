package com.sigaa.config;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CatalogoAerolineaService {

    private final CatalogoAerolineaRepository repo;

    public CatalogoAerolineaService(CatalogoAerolineaRepository repo) {
        this.repo = repo;
    }

    public CatalogoAerolinea crear(CatalogoAerolinea a) {
        return repo.save(a);
    }

    public List<CatalogoAerolinea> listar() {
        return repo.findAll();
    }

    public CatalogoAerolinea actualizar(Long id, CatalogoAerolinea datos) {
        CatalogoAerolinea a = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Aerolinea no encontrada"));

        a.setCodigo(datos.getCodigo());
        a.setNombre(datos.getNombre());

        return repo.save(a);
    }

    public boolean eliminar(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}