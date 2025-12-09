package com.sigaa.auth;

import com.sigaa.seguridad.JwtUtil;
import com.sigaa.seguridad.UsuarioRol;
import com.sigaa.seguridad.UsuarioRolRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final UsuarioService usuarioService;
    private final UsuarioRolRepository usuarioRolRepo;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioService usuarioService,
                       UsuarioRolRepository usuarioRolRepo,
                       JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.usuarioRolRepo = usuarioRolRepo;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse login(LoginRequest req) {

        Usuario u = usuarioService.buscarPorUsuario(req.getUsuario());
        if (u == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // Validar contraseña
        if (!usuarioService.validarPassword(req.getPassword(), u.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Obtener roles reales desde seguridad
        List<UsuarioRol> rolesUsuario = usuarioRolRepo.findByUsuarioId(u.getId());

        List<String> roles = rolesUsuario.stream()
                .map(r -> r.getRol().getNombre())
                .toList();

        // Generar token COMPLETO
        String token = jwtUtil.generarToken(u.getId(), roles);

        return new AuthResponse(
                token,
                u.getId(),
                u.getNombre(),
                u.getUsuario(),
                roles
        );
    }
}
