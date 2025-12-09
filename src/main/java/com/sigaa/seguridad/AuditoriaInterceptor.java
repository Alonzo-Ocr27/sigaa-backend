package com.sigaa.seguridad;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuditoriaInterceptor implements HandlerInterceptor {

    private final AuditoriaService auditoriaService;

    public AuditoriaInterceptor(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {

        // ============================
        // 1. OBTENER USUARIO DEL TOKEN
        // ============================
        Long usuarioId = null;

        Object userAttr = request.getAttribute("usuarioId");

        if (userAttr instanceof Long id) {
            usuarioId = id;
        } else if (userAttr instanceof Integer i) {
            usuarioId = i.longValue();
        }

        if (usuarioId == null) {
            usuarioId = 0L; // usuario no autenticado
        }

        // ============================
        // 2. DETECTAR MÓDULO
        // ============================
        String uri = request.getRequestURI();      // /api/vuelos/crear
        String modulo = "DESCONOCIDO";

        if (uri.startsWith("/api/")) {
            String resto = uri.substring(5);

            int slash = resto.indexOf('/');
            if (slash > 0) {
                modulo = resto.substring(0, slash).toUpperCase();
            } else {
                modulo = resto.toUpperCase();
            }
        }

        // ============================
        // 3. OBTENER ACCIÓN Y URL
        // ============================
        String accion = request.getMethod(); // GET, POST, PUT, DELETE
        String detalle = uri;
        String ip = request.getRemoteAddr();

        // ============================
        // 4. GUARDAR AUDITORÍA
        // ============================
        try {
            auditoriaService.registrar(
                    usuarioId,
                    modulo,
                    accion,
                    detalle,
                    ip
            );
        } catch (Exception ignored) {
            // No se debe romper la petición
        }
    }
}