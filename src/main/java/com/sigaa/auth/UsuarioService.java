package com.sigaa.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public List<Usuario> listar() {
        return repo.findAll();
    }

    public Usuario crear(Usuario u) {

        if (repo.findByUsuario(u.getUsuario()) != null) {
            throw new RuntimeException("El usuario ya existe");
        }

        // Encriptar contraseña
        u.setPassword(encoder.encode(u.getPassword()));

        return repo.save(u);
    }

    public Usuario buscarPorUsuario(String usuario) {
        return repo.findByUsuario(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public boolean validarPassword(String raw, String hash) {
        return encoder.matches(raw, hash);
    }
}