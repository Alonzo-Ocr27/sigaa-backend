package com.sigaa.gates;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GateAsignacionRepository extends JpaRepository<GateAsignacion, Long> {

    // Buscar asignaciones de un vuelo
    List<GateAsignacion> findByVueloId(Long vueloId);

    // Para validar si el vuelo ya tiene una gate asignada ACTIVAMENTE
    GateAsignacion findByVueloIdAndEstado(Long vueloId, String estado);
}
