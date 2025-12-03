package com.sigaa.comercial;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArrendatarioService {

    private final ArrendatarioRepository repo;

    public ArrendatarioService(ArrendatarioRepository repo) {
        this.repo = repo;
    }

    // =============================
    // CREAR
    // =============================
    public Arrendatario crear(Arrendatario a) {

        // Validar RUC único
        if (repo.existsByRuc(a.getRuc())) {
            throw new RuntimeException("El RUC ya está registrado");
        }

        return repo.save(a);
    }

    // =============================
    // LISTAR
    // =============================
    public List<Arrendatario> listar() {
        return repo.findAll();
    }

    // =============================
    // BUSCAR POR ID
    // =============================
    public Arrendatario buscar(Long id) {
        return repo.findById(id).orElse(null);
    }

    // =============================
    // ACTUALIZAR
    // =============================
    public Arrendatario actualizar(Long id, Arrendatario datos) {

        Arrendatario a = buscar(id);
        if (a == null) {
            return null;
        }

        // Validación: si cambia el RUC, validar que no exista otro igual
        if (!a.getRuc().equals(datos.getRuc()) &&
                repo.existsByRuc(datos.getRuc())) {
            throw new RuntimeException("Este RUC ya está en uso por otro arrendatario");
        }

        a.setNombreEmpresa(datos.getNombreEmpresa());
        a.setRuc(datos.getRuc());
        a.setRepresentante(datos.getRepresentante());
        a.setTelefono(datos.getTelefono());
        a.setEmail(datos.getEmail());

        return repo.save(a);
    }
}