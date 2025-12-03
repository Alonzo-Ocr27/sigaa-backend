package com.sigaa.brs;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MaletaRepository extends JpaRepository<Maleta, Long> {

    Maleta findByTagCode(String tagCode);

    List<Maleta> findByVueloId(Long vueloId);

    List<Maleta> findByPasajeroCheckinId(Long pasajeroCheckinId);

    List<Maleta> findByEstado(String estado);

    long countByEstado(String estado);

    long countByEstadoAndVueloId(String estado, Long vueloId);

    long countByVueloId(Long vueloId);
}
