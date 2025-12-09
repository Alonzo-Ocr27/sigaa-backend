package com.sigaa.auth;

import com.sigaa.common.ApiResponse;
import com.sigaa.seguridad.JwtUtil;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
        public ApiResponse<String> login(@RequestBody LoginRequest req) {

            Usuario u = usuarioService.buscarPorUsuario(req.getUsuario());

            if (u == null) {
                return new ApiResponse<>(false, "Credenciales incorrectas", null);
            }

            // Validar contraseña encriptada
            boolean ok = usuarioService.validarPassword(req.getPassword(), u.getPassword());

            if (!ok) {
                return new ApiResponse<>(false, "Credenciales incorrectas", null);
            }

            // Generar token simple si estás sin roles complejos
            String token = jwtUtil.generarToken(u.getId(), List.of(u.getRol()));

            return new ApiResponse<>(true, "Login exitoso", token);
        }

}