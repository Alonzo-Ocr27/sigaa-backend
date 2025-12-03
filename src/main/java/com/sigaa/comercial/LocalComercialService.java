package com.sigaa.comercial;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalComercialService {

    private final LocalComercialRepository repo;

    public LocalComercialService(LocalComercialRepository repo) {
        this.repo = repo;
    }

    public LocalComercial crear(LocalComercial l) {
        if (l.getEstado() == null || l.getEstado().isEmpty()) {
            l.setEstado("DISPONIBLE");
        }
        return repo.save(l);
    }

    public List<LocalComercial> listar() {
        return repo.findAll();
    }

    public LocalComercial buscar(Long id) {
        return repo.findById(id).orElse(null);
    }

    public LocalComercial actualizar(Long id, LocalComercial datos) {
        LocalComercial l = buscar(id);
        if (l == null) return null;

        l.setCodigo(datos.getCodigo());
        l.setMetrosCuadrados(datos.getMetrosCuadrados());
        l.setUbicacion(datos.getUbicacion());
        l.setPrecioBase(datos.getPrecioBase());

        return repo.save(l);
    }

    public boolean cambiarEstado(Long id, String estado) {
        LocalComercial l = buscar(id);
        if (l == null) return false;
        l.setEstado(estado);
        repo.save(l);
        return true;
    }
}