package com.sigaa.checkin;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PasajeroCheckinRepository extends JpaRepository<PasajeroCheckin, Long> {

    List<PasajeroCheckin> findByVueloId(Long vueloId);

    PasajeroCheckin findByVueloIdAndAsiento(Long vueloId, String asiento);

    PasajeroCheckin findByCodigoBoardingPass(String codigoBoardingPass);

    long countByEstado(String estado);

    long countByVueloId(Long vueloId);
}