package com.sigaa.reportes;

import com.sigaa.checkin.PasajeroCheckinRepository;
import com.sigaa.vuelos.Vuelo;
import com.sigaa.vuelos.VueloRepository;
import org.springframework.stereotype.Service;

@Service
public class ReporteCheckinService {

    private final PasajeroCheckinRepository checkinRepo;
    private final VueloRepository vueloRepo;

    public ReporteCheckinService(PasajeroCheckinRepository c, VueloRepository v) {
        this.checkinRepo = c;
        this.vueloRepo = v;
    }

    public ReporteCheckinDTO resumenVuelo(Long vueloId) {

        Vuelo vuelo = vueloRepo.findById(vueloId).orElse(null);
        if (vuelo == null) {
            return new ReporteCheckinDTO("DESCONOCIDO", 0, 0);
        }

        long registrados = checkinRepo.countByVueloId(vueloId);

        // más adelante podrás obtener esto desde otra tabla
        int totalAsientos = 150;

        return new ReporteCheckinDTO(
                vuelo.getNumero(),
                registrados,
                totalAsientos - (int) registrados
        );
    }
}