package com.sigaa.auth;

import com.sigaa.common.ApiResponse;
import com.sigaa.security.JwtUtil;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService service;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/usuarios")
    public List<Usuario> listar() {
        return service.listar();
    }

    @PostMapping("/registrar")
    public ApiResponse<Usuario> registrar(@RequestBody Usuario u) {
        Usuario creado = service.crear(u);
        return new ApiResponse<>(true, "Usuario creado", creado);
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody LoginRequest req) {

        Usuario u = service.buscarPorUsuario(req.getUsuario());

        if (u == null || !u.getPassword().equals(req.getPassword())) {
            return new ApiResponse<>(false, "Credenciales incorrectas", null);
        }

        String token = jwtUtil.generarToken(u.getUsuario(), u.getRol());

        return new ApiResponse<>(true, "Login exitoso", token);
    }

}