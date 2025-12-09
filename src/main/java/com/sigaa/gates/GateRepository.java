package com.sigaa.gates;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GateRepository extends JpaRepository<Gate, Long> {

    // Contar por estado
    long countByEstado(String estado);

    // Validar código único
    boolean existsByCodigo(String codigo);
}
