package com.sigaa.comercial;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArrendatarioRepository extends JpaRepository<Arrendatario, Long> {

    // Buscar por RUC (único en Panamá)
    Arrendatario findByRuc(String ruc);

    // Buscar por nombre de empresa
    Arrendatario findByNombreEmpresa(String nombreEmpresa);

    // Verificar si un RUC ya existe
    boolean existsByRuc(String ruc);
}