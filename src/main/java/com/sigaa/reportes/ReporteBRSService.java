package com.sigaa.reportes;

import com.sigaa.brs.MaletaRepository;
import com.sigaa.vuelos.Vuelo;
import com.sigaa.vuelos.VueloRepository;
import org.springframework.stereotype.Service;

@Service
public class ReporteBRSService {

    private final MaletaRepository maletaRepo;
    private final VueloRepository vueloRepo;

    public ReporteBRSService(MaletaRepository m, VueloRepository v) {
        this.maletaRepo = m;
        this.vueloRepo = v;
    }

    public ReporteBRSAggregadoDTO resumenVuelo(Long vueloId) {

        Vuelo vuelo = vueloRepo.findById(vueloId)
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado: " + vueloId));

        long total = maletaRepo.countByVueloId(vueloId);
        long extraviadas = maletaRepo.countByEstadoAndVueloId("EXTRAVIADA", vueloId);

        return new ReporteBRSAggregadoDTO(
                vuelo.getNumero(),
                total,
                extraviadas
        );
    }
}