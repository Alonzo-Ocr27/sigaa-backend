package com.sigaa.seguridad;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {

    // Buscar rol por nombre (ADMIN, CHECKIN, GATES...)
    Rol findByNombre(String nombre);

    // Saber si existe un rol antes de crearlo
    boolean existsByNombre(String nombre);
}