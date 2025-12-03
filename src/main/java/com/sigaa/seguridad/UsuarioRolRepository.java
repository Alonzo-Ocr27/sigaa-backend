package com.sigaa.seguridad;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {

    // Obtiene todos los roles asignados a un usuario
    List<UsuarioRol> findByUsuarioId(Long usuarioId);

    // Verifica si un usuario ya tiene un rol
    boolean existsByUsuarioIdAndRolId(Long usuarioId, Long rolId);

    // Elimina todos los roles de un usuario (útil para reasignaciones)
    void deleteByUsuarioId(Long usuarioId);
}