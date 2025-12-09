package com.sigaa.gates;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GateAsignacionRepository extends JpaRepository<GateAsignacion, Long> {

    // Asignaciones para un vuelo
    List<GateAsignacion> findByVueloId(Long vueloId);

    // Validar si ya tiene una gate activa
    GateAsignacion findByVueloIdAndEstado(Long vueloId, String estado);
}
