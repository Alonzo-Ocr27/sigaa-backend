package com.sigaa.gates;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GateRepository extends JpaRepository<Gate, Long> {

    // Contar por estado (LIBRE, OCUPADA, MANTENIMIENTO)
    long countByEstado(String estado);

    // Validar que no existan códigos duplicados
    boolean existsByCodigo(String codigo);
}