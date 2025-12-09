package com.sigaa.dashboard;

import com.sigaa.common.ApiResponse;
import com.sigaa.vuelos.Vuelo;
import com.sigaa.vuelos.VueloRepository;
import com.sigaa.checkin.PasajeroCheckinRepository;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    private final VueloRepository vueloRepo;
    private final PasajeroCheckinRepository checkinRepo;
    private final NotificacionDashboardService notiService;

    public DashboardController(
            VueloRepository vueloRepo,
            PasajeroCheckinRepository checkinRepo,
            NotificacionDashboardService notiService
    ) {
        this.vueloRepo = vueloRepo;
        this.checkinRepo = checkinRepo;
        this.notiService = notiService;
    }

    // ---------------------------------------------------------
    // 1️⃣ RESUMEN GENERAL
    // ---------------------------------------------------------
    @GetMapping("/resumen")
    public ApiResponse<Map<String, Object>> resumen() {

        long activos = vueloRepo.countByEstado("PROGRAMADO");
        long pasajerosTerminal = checkinRepo.count();
        long alertas = notiService.contarNuevas();

        Map<String, Object> data = new HashMap<>();
        data.put("vuelosActivos", activos);
        data.put("pasajerosTerminal", pasajerosTerminal);
        data.put("alertas", alertas);

        return new ApiResponse<>(true, "OK", data);
    }

    // ---------------------------------------------------------
    // 2️⃣ PRÓXIMOS VUELOS
    // ---------------------------------------------------------
    @GetMapping("/proximos")
    public ApiResponse<List<Vuelo>> proximos() {

        LocalDateTime ahora = LocalDateTime.now().minusHours(3); // margen seguro

        List<Vuelo> lista =
            vueloRepo.findTop10ByFechaHoraSalidaGreaterThanEqualOrderByFechaHoraSalidaAsc(ahora);

        return new ApiResponse<>(true, "OK", lista);
    }



    // ---------------------------------------------------------
    // 3️⃣ OCUPACIÓN
    // ---------------------------------------------------------
    @GetMapping("/ocupacion")
    public ApiResponse<Map<String, Object>> ocupacion() {

        long totalVuelos = vueloRepo.count();
        long llenos = vueloRepo.countByEstado("LLENO");

        long totalAsientos = totalVuelos * 150;   // SIMPLIFICADO
        long ocupados = checkinRepo.count();
        long libres = totalAsientos - ocupados;

        int promedio = totalVuelos == 0 ? 0 :
                (int)((ocupados * 100) / totalAsientos);

        Map<String, Object> data = new HashMap<>();
        data.put("llenos", llenos);
        data.put("libres", libres);
        data.put("promedio", promedio);

        return new ApiResponse<>(true, "OK", data);
    }
}