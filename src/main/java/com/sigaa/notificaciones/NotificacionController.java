package com.sigaa.notificaciones;

import com.sigaa.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService service;

    public NotificacionController(NotificacionService service) {
        this.service = service;
    }

    // ====================================================
    // 📌 1. Encolar notificación manual
    // ====================================================
    @PostMapping("/encolar")
    public ApiResponse<Notificacion> encolar(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(defaultValue = "SISTEMA") String canal,
            @RequestParam String titulo,
            @RequestParam String mensaje
    ) {
        Notificacion n = service.encolar(usuarioId, canal, titulo, mensaje);
        return new ApiResponse<>(true, "Notificación encolada", n);
    }

    // ====================================================
    // 📌 2. Enviar una notificación en cola
    // ====================================================
    @PutMapping("/{id}/enviar")
    public ApiResponse<Notificacion> enviar(@PathVariable Long id) {
        Notificacion n = service.enviarAhora(id);
        return new ApiResponse<>(n != null, n != null ? "Notificación procesada" : "No encontrada", n);
    }

    // ====================================================
    // 📌 3. Enviar todas las pendientes
    // ====================================================
    @PutMapping("/enviar-pendientes")
    public ApiResponse<Long> enviarPendientes() {
        long total = service.enviarPendientes();
        return new ApiResponse<>(true, "Notificaciones procesadas: " + total, total);
    }

    // ====================================================
    // 📌 4. Listar pendientes
    // ====================================================
    @GetMapping("/pendientes")
    public List<Notificacion> pendientes() {
        return service.listarPendientes();
    }

    // ====================================================
    // 📌 5. Historial por usuario
    // ====================================================
    @GetMapping("/usuario/{usuarioId}")
    public List<Notificacion> porUsuario(@PathVariable Long usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }

    // ====================================================
    // 📌 6. Enviar directamente sin encolar
    // ====================================================
    @PostMapping("/enviar-directo")
    public ApiResponse<String> enviarDirecto(
            @RequestParam String titulo,
            @RequestParam String mensaje
    ) {
        service.enviar(titulo, mensaje);
        return new ApiResponse<>(true, "Notificación enviada directamente", null);
    }

    // ====================================================
    // 📌 7. Listar todas con manejo de errores interno
    // ====================================================
    @GetMapping
    public Object listarTodas() {
        try {
            return service.listarTodas();
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false,
                    "Error interno: " + e.getClass().getSimpleName() + " - " + e.getMessage(),
                    null);
        }
    }

    // ====================================================
    // 📌 8. Estadísticas para Dashboard (total de pendientes)
    // ====================================================
    @GetMapping("/nuevas")
    public ApiResponse<Map<String, Long>> nuevas() {

        long total = service.contarPendientes();

        Map<String, Long> data = new HashMap<>();
        data.put("total", total);

        return new ApiResponse<>(true, "OK", data);
    }

}