package com.sigaa.comercial;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacturaComercialRepository extends JpaRepository<FacturaComercial, Long> {

    List<FacturaComercial> findByContratoId(Long contratoId);

    List<FacturaComercial> findByPagada(boolean pagada);

    long countByPagada(boolean b);

    long countByEstado(String string);
}
