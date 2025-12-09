package com.sigaa.dashboard;

import com.sigaa.notificaciones.NotificacionRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificacionDashboardService {

    private final NotificacionRepository repo;

    public NotificacionDashboardService(NotificacionRepository repo) {
        this.repo = repo;
    }

    public long contarNuevas() {
        return repo.countByEstado("PENDIENTE");
    }
}