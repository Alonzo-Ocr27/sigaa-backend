package com.sigaa.notificaciones;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    // Notificaciones pendientes de envío
    List<Notificacion> findByEstado(String estado);

    // Historial de un usuario
    List<Notificacion> findByUsuarioIdOrderByFechaCreacionDesc(Long usuarioId);
}