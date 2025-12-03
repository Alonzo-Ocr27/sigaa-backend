package com.sigaa.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // buscar usuario exacto (para login)
    Usuario findByUsuario(String usuario);

    // opcional: verificar si existe
    boolean existsByUsuario(String usuario);
}