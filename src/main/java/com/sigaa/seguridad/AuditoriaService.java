package com.sigaa.seguridad;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuditoriaService {

    private final AuditoriaRepository repo;

    public AuditoriaService(AuditoriaRepository repo) {
        this.repo = repo;
    }

    public void registrar(Long usuarioId, String modulo, String accion, String detalle, String ip) {

        Auditoria a = new Auditoria();

        a.setUsuarioId(usuarioId != null ? usuarioId : 0L);
        a.setModulo(modulo != null ? modulo.trim() : "DESCONOCIDO");
        a.setAccion(accion != null ? accion.trim() : "SIN_ACCION");
        a.setDetalle(detalle != null ? detalle.trim() : "");
        a.setIp(ip != null ? ip : "0.0.0.0");
        a.setFechaHora(LocalDateTime.now());

        repo.save(a);
    }
}