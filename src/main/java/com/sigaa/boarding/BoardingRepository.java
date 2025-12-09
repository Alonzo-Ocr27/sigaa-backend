package com.sigaa.boarding;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BoardingRepository extends JpaRepository<BoardingRegistro, Long> {

    BoardingRegistro findByCodigoBoardingPass(String codigo);

    List<BoardingRegistro> findByVueloId(Long vueloId);
}