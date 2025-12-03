package com.sigaa.comercial;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratoComercialRepository extends JpaRepository<ContratoComercial, Long> {

    List<ContratoComercial> findByEstado(String estado);

    long countByEstado(String string);
}