package com.sigaa.notificaciones;

import com.sigaa.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService service;

    public NotificacionController(NotificacionService service) {
        this.service = service;
    }

    // ====================================================
    // 📌 1. Encolar notificación manual (no enviada todavía)
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
    public ApiResponse<Integer> enviarPendientes() {
        int total = service.enviarPendientes();
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
    // 📌 6. Enviar directamente sin encolar (compatibilidad)
    // ====================================================
    @PostMapping("/enviar-directo")
    public ApiResponse<String> enviarDirecto(
            @RequestParam String titulo,
            @RequestParam String mensaje
    ) {
        service.enviar(titulo, mensaje);
        return new ApiResponse<>(true, "Notificación enviada directamente", null);
    }

    @GetMapping
    public Object listarTodas() {
        try {
            return service.listarTodas();
        } catch (Exception e) {
            e.printStackTrace(); // imprime stacktrace en la consola del servidor
            return new ApiResponse<>(false, "Error interno: " + e.getClass().getSimpleName() + " - " + e.getMessage(), null);
        }
    }

}