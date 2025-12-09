package com.sigaa.notificaciones;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacionService {

    private final NotificacionRepository repo;

    public NotificacionService(NotificacionRepository repo) {
        this.repo = repo;
    }

    // ============================================================
    // MÉTODO DE COMPATIBILIDAD (llamado desde Vuelos, BRS, etc.)
    // ============================================================
    public void enviar(String titulo, String mensaje) {

        String canal = "SISTEMA";
        Long usuarioId = null;

        Notificacion n = encolar(usuarioId, canal, titulo, mensaje);
        enviarAhora(n.getId());
    }

    // ============================================================
    // ENCOLAR UNA NOTIFICACIÓN
    // ============================================================
    public Notificacion encolar(Long usuarioId, String canal,
                                String titulo, String mensaje) {

        if (canal == null || canal.trim().isEmpty()) {
            canal = "SISTEMA";
        }

        if (titulo == null) titulo = "";
        if (mensaje == null) mensaje = "";

        Notificacion n = new Notificacion();
        n.setUsuarioId(usuarioId);
        n.setCanal(canal.toUpperCase());
        n.setTitulo(titulo);
        n.setMensaje(mensaje);
        n.setEstado("PENDIENTE");
        n.setFechaCreacion(LocalDateTime.now());

        return repo.save(n);
    }

    // ============================================================
    // ENVIAR UNA NOTIFICACIÓN INDIVIDUAL
    // ============================================================
    public Notificacion enviarAhora(Long id) {

        Notificacion n = repo.findById(id).orElse(null);
        if (n == null) return null;

        try {
            n.setEstado("ENVIADA");
            n.setFechaEnvio(LocalDateTime.now());
            n.setErrorDetalle(null);

        } catch (Exception e) {
            n.setEstado("ERROR");
            n.setErrorDetalle(e.getMessage());
        }

        return repo.save(n);
    }

    // ============================================================
    // ENVIAR TODAS LAS PENDIENTES
    // ============================================================
    public long enviarPendientes() {
        List<Notificacion> pendientes = repo.findByEstado("PENDIENTE");
        long total = 0;

        for (Notificacion n : pendientes) {
            enviarAhora(n.getId());
            total++;
        }

        return total;
    }

    // ============================================================
    // CONSULTAS
    // ============================================================
    public List<Notificacion> listarPendientes() {
        return repo.findByEstado("PENDIENTE");
    }

    public List<Notificacion> listarPorUsuario(Long usuarioId) {
        return repo.findByUsuarioIdOrderByFechaCreacionDesc(usuarioId);
    }

    public List<Notificacion> listarTodas() {
        return repo.findAll();
    }

    public long contarPendientes() {
        return repo.countByEstado("PENDIENTENTE");
    }
}