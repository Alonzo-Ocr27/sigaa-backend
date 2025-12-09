package com.sigaa.vuelos;

import com.sigaa.reportes.ReporteVuelosDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface VueloRepository extends JpaRepository<Vuelo, Long> {

    List<Vuelo> findByEstado(String estado);

    long countByEstado(String estado);

    List<Vuelo> findByOrigen(String origen);

    List<Vuelo> findByDestino(String destino);

    @Query("SELECT v FROM Vuelo v WHERE v.estado = ?1 AND v.origen = ?2")
    List<Vuelo> findByEstadoAndOrigen(String estado, String origen);

    // ============================
    //      DASHBOARD
    // ============================

    // Todos los vuelos ordenados
    List<Vuelo> findTop10ByOrderByFechaHoraSalidaAsc();

    // Solo vuelos PROGRAMADOS o ACTIVOS
    List<Vuelo> findTop10ByEstadoOrderByFechaHoraSalidaAsc(String estado);
    List<Vuelo> findTop10ByFechaHoraSalidaGreaterThanEqualOrderByFechaHoraSalidaAsc(LocalDateTime fecha);


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