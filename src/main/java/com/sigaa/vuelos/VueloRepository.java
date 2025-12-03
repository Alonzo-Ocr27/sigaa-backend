package com.sigaa.vuelos;

import com.sigaa.reportes.ReporteVuelosDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VueloRepository extends JpaRepository<Vuelo, Long> {

    List<Vuelo> findByEstado(String estado);

    long countByEstado(String estado);

    List<Vuelo> findByOrigen(String origen);

    List<Vuelo> findByDestino(String destino);

    @Query("SELECT v FROM Vuelo v WHERE v.estado = ?1 AND v.origen = ?2")
    List<Vuelo> findByEstadoAndOrigen(String estado, String origen);

    // ============================
    //      CONSULTA DE REPORTE
    // ============================
    @Query("""
           SELECT new com.sigaa.reportes.ReporteVuelosDTO(
                v.numero,
                v.origen,
                v.destino,
                v.fechaHoraSalida,
                v.fechaHoraLlegada,
                v.estado,
                v.tipoAeronave
           )
           FROM Vuelo v
           """)
    List<ReporteVuelosDTO> findAllVuelosReporte();
}
