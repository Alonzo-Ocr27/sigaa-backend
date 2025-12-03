package com.sigaa.security;

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

        // RUTAS PÚBLICAS
        if (uri.startsWith("/api/auth/login") ||
            uri.startsWith("/api/auth/registrar") ||
            uri.startsWith("/api/notificaciones") ||
            req.getRequestURI().contains("/api/vuelos") ||
            req.getRequestURI().contains("/api/configuracion") ||
            req.getRequestURI().contains("/api/reservas") ||
            req.getRequestURI().contains("/api/pagos") ||
            req.getRequestURI().contains("/api/usuarios") ||
            req.getRequestURI().contains("/api/aerolineas") ||
            req.getRequestURI().contains("/api/destinos") ||
            req.getRequestURI().contains("/api/aeronaves") ||
            req.getRequestURI().contains("/api/paradas") ||
            req.getRequestURI().contains("/api/reportes") ||
            req.getRequestURI().contains("/api/comercial") ||
            req.getRequestURI().contains("/api/documentacion") ||
            req.getRequestURI().contains("/api/checkin") ||
            req.getRequestURI().contains("/api/public") ||
            req.getRequestURI().contains("/api/test") ||
            req.getRequestURI().contains("/api/swagger-ui/") ||
            req.getRequestURI().contains("/api/v3/api-docs")) {

            chain.doFilter(request, response);
            return;
        }

        // VALIDAR TOKEN
        if (auth == null || !auth.startsWith("Bearer ")) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token requerido");
            return;
        }

        try {
            String token = auth.substring(7);
            Claims claims = jwtUtil.validarToken(token);
            request.setAttribute("rol", claims.get("rol"));
        } catch (Exception e) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
            return;
        }

        chain.doFilter(request, response);
    }
}