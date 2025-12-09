package com.sigaa.seguridad;

import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String auth = req.getHeader("Authorization");

        // Permitir preflight CORS
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        // ==== RUTAS PÚBLICAS SIN TOKEN ====
        if (esRutaPublica(uri)) {
            chain.doFilter(request, response);
            return;
        }

        // ==== VALIDAR TOKEN ====
        if (auth == null || !auth.startsWith("Bearer ")) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token requerido");
            return;
        }

        try {
            String token = auth.substring(7);
            Claims claims = jwtUtil.validarToken(token);

            Long usuarioId = claims.get("usuarioId", Long.class);
            List<String> roles = (List<String>) claims.get("roles");

            // Guardamos en request para usarlo en auditoría o permisos
            req.setAttribute("usuarioId", usuarioId);
            req.setAttribute("roles", roles);

        } catch (Exception e) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
            return;
        }

        chain.doFilter(request, response);
    }


    // -----------------------
    //  RUTAS PÚBLICAS
    // -----------------------
    private boolean esRutaPublica(String uri) {
        return uri.startsWith("/api/auth/")
            || uri.startsWith("/api/public/")
            || uri.startsWith("/api/vuelos/")
            || uri.startsWith("/api/embarque/")
            || uri.startsWith("/api/checkin/")
            || uri.startsWith("/api/comercial/")
            || uri.startsWith("/api/reportes/")
            || uri.startsWith("/api/notificaciones/")
            || uri.startsWith("/api/dashboard/")
            || uri.startsWith("/api/config/");
        }



}