package com.sigaa.seguridad;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RolPermisoRepository extends JpaRepository<RolPermiso, Long> {

    // Permisos asignados a un rol
    List<RolPermiso> findByRolId(Long rolId);

    // Para verificar si un rol YA tiene un permiso específico
    boolean existsByRolIdAndPermisoId(Long rolId, Long permisoId);

    // Listar por permiso (útil para auditorías)
    List<RolPermiso> findByPermisoId(Long permisoId);
}