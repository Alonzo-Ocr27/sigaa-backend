package com.sigaa.seguridad;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {

    // Buscar permiso por su código exacto
    Permiso findByCodigo(String codigo);

    // Buscar permisos que comiencen con un módulo: VUELOS_, CHECKIN_, etc.
    List<Permiso> findByCodigoStartingWith(String prefijo);

    // Buscar varios permisos a la vez
    List<Permiso> findByCodigoIn(List<String> codigos);
}
