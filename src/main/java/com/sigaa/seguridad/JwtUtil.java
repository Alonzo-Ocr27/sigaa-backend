package com.sigaa.seguridad;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private static final String SECRET = "MiClaveSuperSecretaParaElSIGAA123456789";
    private static final long EXPIRACION = 1000 * 60 * 60 * 8; // 8 horas

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generarToken(Long usuarioId, List<String> roles) {
        return Jwts.builder()
                .claim("usuarioId", usuarioId)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACION))
                .signWith(key)
                .compact();
    }

    public Claims validarToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
