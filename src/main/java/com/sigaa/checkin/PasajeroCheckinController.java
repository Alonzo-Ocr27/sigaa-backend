package com.sigaa.checkin;

import com.sigaa.common.ApiResponse;
import com.sigaa.seguridad.SeguridadService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/checkin")
@CrossOrigin("*")
public class PasajeroCheckinController {

    private final PasajeroCheckinService service;
    private final SeguridadService seguridadService;
    private final PasajeroCheckinRepository repo;

    // 🔥 Constructor completo con repo
    public PasajeroCheckinController(
            PasajeroCheckinService service,
            SeguridadService seguridadService,
            PasajeroCheckinRepository repo) {

        this.service = service;
        this.seguridadService = seguridadService;
        this.repo = repo;
    }

    // ======================================================
    //                 CREAR CHECK-IN
    //                 Permiso: CHECKIN_CREAR
    // ======================================================
    @PostMapping("/crear")
    public ApiResponse<PasajeroCheckinDTO> crear(
            HttpServletRequest request,
            @RequestBody PasajeroCheckin p) {

        Long userId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(userId, "CHECKIN_CREAR")) {
            throw new RuntimeException("No tiene permiso para registrar check-in");
        }

        return new ApiResponse<>(true, "Check-in completado", service.hacerCheckin(p));
    }

    // ======================================================
    //        LISTAR CHECK-IN POR VUELO (LISTAR)
    // ======================================================
    @GetMapping("/vuelo/{vueloId}")
    public List<PasajeroCheckinDTO> listarPorVuelo(
            HttpServletRequest request,
            @PathVariable Long vueloId) {

        Long userId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(userId, "CHECKIN_LISTAR")) {
            throw new RuntimeException("No tiene permiso para ver los check-in del vuelo");
        }

        return service.listarPorVuelo(vueloId);
    }

    // ======================================================
    //                CANCELAR CHECK-IN
    // ======================================================
    @PutMapping("/{id}/cancelar")
    public ApiResponse<String> cancelar(
            HttpServletRequest request,
            @PathVariable Long id) {

        Long userId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(userId, "CHECKIN_CANCELAR")) {
            throw new RuntimeException("No tiene permiso para cancelar check-in");
        }

        boolean ok = service.cancelar(id);
        return new ApiResponse<>(ok, ok ? "Check-in cancelado" : "Pasajero no encontrado", null);
    }

    // ======================================================
    //                CAMBIAR ASIENTO
    // ======================================================
    @PutMapping("/{id}/asiento")
    public ApiResponse<PasajeroCheckinDTO> cambiarAsiento(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestParam String nuevoAsiento) {

        Long userId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(userId, "CHECKIN_EDITAR")) {
            throw new RuntimeException("No tiene permiso para cambiar asiento");
        }

        PasajeroCheckinDTO dto = service.actualizarAsiento(id, nuevoAsiento);
        return new ApiResponse<>(dto != null, dto != null ? "Asiento actualizado" : "Pasajero no encontrado", dto);
    }

    // ======================================================
    //      BUSCAR PASAJERO POR BOARDING PASS
    // ======================================================
    @GetMapping("/boardingpass/{codigo}")
    public PasajeroCheckinDTO buscarPorBoarding(
            HttpServletRequest request,
            @PathVariable String codigo) {

        Long userId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(userId, "CHECKIN_LISTAR")) {
            throw new RuntimeException("No tiene permiso para consultar check-in por boarding pass");
        }

        return service.buscarPorBoardingPass(codigo);
    }

    // ======================================================
    //               ESTADÍSTICAS PARA DASHBOARD
    // ======================================================
    @GetMapping("/estadisticas")
    public ApiResponse<Map<String, Object>> estadisticas() {

        long total = repo.count();
        long completados = repo.countByEstado("COMPLETADO");
        long cancelados = repo.countByEstado("CANCELADO");

        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("completados", completados);
        data.put("cancelados", cancelados);
        data.put("pasajeros", completados); // los que están en terminal

        return new ApiResponse<>(true, "OK", data);
    }

}