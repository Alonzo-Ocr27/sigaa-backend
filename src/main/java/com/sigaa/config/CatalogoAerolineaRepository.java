package com.sigaa.config;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogoAerolineaRepository extends JpaRepository<CatalogoAerolinea, Long> {

    // Para validar duplicados
    boolean existsByCodigo(String codigo);
}
