package com.sigaa.comercial;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LocalComercialRepository extends JpaRepository<LocalComercial, Long> {

    // Buscar por estado: DISPONIBLE, OCUPADO, EN_MANTENIMIENTO
    List<LocalComercial> findByEstado(String estado);

    // Buscar por código para validar duplicados
    LocalComercial findByCodigo(String codigo);

    // Filtrar por área
    List<LocalComercial> findByMetrosCuadradosGreaterThan(double m2);

    // Filtrar por ubicación
    List<LocalComercial> findByUbicacion(String ubicacion);
}