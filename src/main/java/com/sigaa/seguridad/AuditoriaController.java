package com.sigaa.seguridad;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
@CrossOrigin("*")
public class AuditoriaController {

    private final AuditoriaRepository repo;

    public AuditoriaController(AuditoriaRepository repo) {
        this.repo = repo;
    }

    // ================================
    // LISTAR TODO
    // ================================
    @GetMapping("/listar")
    public List<Auditoria> listar() {
        return repo.findAll();
    }

    // ================================
    // BUSCAR POR ID
    // ================================
    @GetMapping("/{id}")
    public Auditoria buscar(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado: " + id));
    }

    // ================================
    // FILTRAR POR USUARIO
    // ================================
    @GetMapping("/usuario/{usuarioId}")
    public List<Auditoria> porUsuario(@PathVariable Long usuarioId) {
        return repo.findAll().stream()
                .filter(a -> usuarioId.equals(a.getUsuarioId()))
                .toList();
    }

    // ================================
    // FILTRAR POR MÓDULO
    // ================================
    @GetMapping("/modulo/{modulo}")
    public List<Auditoria> porModulo(@PathVariable String modulo) {
        return repo.findAll().stream()
                .filter(a -> a.getModulo() != null && a.getModulo().equalsIgnoreCase(modulo))
                .toList();
    }

    // ================================
    // FILTRAR POR ACCIÓN (GET, POST, etc.)
    // ================================
    @GetMapping("/accion/{accion}")
    public List<Auditoria> porAccion(@PathVariable String accion) {
        return repo.findAll().stream()
                .filter(a -> a.getAccion() != null && a.getAccion().equalsIgnoreCase(accion))
                .toList();
    }
}