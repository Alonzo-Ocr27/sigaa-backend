package com.sigaa.auth;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public List<Usuario> listar() {
        return repo.findAll();
    }

    public Usuario crear(Usuario u) {

        // Validar duplicados
        if (repo.findByUsuario(u.getUsuario()) != null) {
            throw new RuntimeException("El usuario ya existe");
        }

        return repo.save(u);
    }

    public Usuario buscarPorUsuario(String usuario) {
        return repo.findByUsuario(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }
}