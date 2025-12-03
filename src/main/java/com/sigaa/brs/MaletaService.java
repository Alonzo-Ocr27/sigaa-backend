package com.sigaa.brs;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaletaService {

    private final MaletaRepository repo;

    public MaletaService(MaletaRepository repo) {
        this.repo = repo;
    }

    public Maleta registrar(Maleta m) {
        // Verificar que no exista otro tag igual
        Maleta existente = repo.findByTagCode(m.getTagCode());
        if (existente != null) {
            throw new RuntimeException("La etiqueta de maleta ya existe");
        }

        // Estado inicial
        m.setEstado("REGISTRADA");
        if (m.getUbicacionActual() == null || m.getUbicacionActual().isEmpty()) {
            m.setUbicacionActual("MOSTRADOR CHECK-IN");
        }

        return repo.save(m);
    }

    public Maleta buscarPorTag(String tagCode) {
        return repo.findByTagCode(tagCode);
    }

    public List<Maleta> listarPorVuelo(Long vueloId) {
        return repo.findByVueloId(vueloId);
    }

    public List<Maleta> listarPorPasajero(Long pasajeroCheckinId) {
        return repo.findByPasajeroCheckinId(pasajeroCheckinId);
    }

    public Maleta cambiarEstado(Long id, String estado) {
        Maleta m = repo.findById(id).orElse(null);
        if (m == null) return null;
        m.setEstado(estado);
        return repo.save(m);
    }

    public Maleta actualizarUbicacion(Long id, String ubicacion) {
        Maleta m = repo.findById(id).orElse(null);
        if (m == null) return null;
        m.setUbicacionActual(ubicacion);
        return repo.save(m);
    }

    public Maleta marcarExtraviada(Long id) {
        Maleta m = repo.findById(id).orElse(null);
        if (m == null) return null;
        m.setEstado("EXTRAVIADA");
        return repo.save(m);
    }
}