package com.sigaa.config;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParametroSistemaRepository extends JpaRepository<ParametroSistema, Long> {
    ParametroSistema findByClave(String clave);
}