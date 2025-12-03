package com.sigaa.boarding;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BoardingRepository extends JpaRepository<BoardingRegistro, Long> {

    // verificar duplicado
    BoardingRegistro findByCodigoBoardingPass(String codigo);

    // listar embarques de un vuelo
    List<BoardingRegistro> findByVueloId(Long vueloId);
}