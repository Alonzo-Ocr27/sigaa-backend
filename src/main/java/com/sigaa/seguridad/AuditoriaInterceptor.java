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
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        // aquí solo capturamos el usuario y lo dejamos en atributos de request
        String userHeader = request.getHeader("X-USER-ID");
        if (userHeader != null && !userHeader.isEmpty()) {
            try {
                Long usuarioId = Long.parseLong(userHeader);
                request.setAttribute("usuarioId", usuarioId);
            } catch (NumberFormatException e) {
                // si viene mal, lo ignoramos
            }
        }

        return true; // seguir con la petición
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        Object userAttr = request.getAttribute("usuarioId");
        Long usuarioId = null;

        if (userAttr instanceof Long id) {
            usuarioId = id;
        } else if (userAttr instanceof String s) {
            try { usuarioId = Long.parseLong(s); } catch (Exception ignored) {}
        }


        // módulo = primer segmento luego de /api/
        String uri = request.getRequestURI();   // ej: /api/vuelos/crear
        String modulo = "DESCONOCIDO";

        String path = uri;
        if (uri.startsWith("/api/")) {
            String resto = uri.substring(5); // quitar /api/
            int slash = resto.indexOf('/');
            if (slash >= 0) {
                modulo = resto.substring(0, slash).toUpperCase();
            } else {
                modulo = resto.toUpperCase();
            }
        }

        String accion = request.getMethod(); // GET, POST, PUT, DELETE, etc.
        String detalle = path;
        String ip = request.getRemoteAddr();

        try {
            auditoriaService.registrar(
                    usuarioId,
                    modulo,
                    accion,
                    detalle,
                    ip
            );
        } catch (Exception ignored) {
            // evitamos que la auditoría rompa la petición
        }
    }
}