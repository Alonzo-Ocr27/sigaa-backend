package com.sigaa.checkin;

import com.sigaa.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkin")
public class PasajeroCheckinController {

    private final PasajeroCheckinService service;

    public PasajeroCheckinController(PasajeroCheckinService service) {
        this.service = service;
    }

    @PostMapping("/crear")
    public ApiResponse<PasajeroCheckinDTO> crear(@RequestBody PasajeroCheckin p) {
        return new ApiResponse<>(true, "Check-in completado", service.hacerCheckin(p));
    }

    @GetMapping("/vuelo/{vueloId}")
    public List<PasajeroCheckinDTO> listarPorVuelo(@PathVariable Long vueloId) {
        return service.listarPorVuelo(vueloId);
    }

    @PutMapping("/{id}/cancelar")
    public ApiResponse<String> cancelar(@PathVariable Long id) {
        boolean ok = service.cancelar(id);
        return new ApiResponse<>(ok, ok ? "Check-in cancelado" : "Pasajero no encontrado", null);
    }

    @PutMapping("/{id}/asiento")
    public ApiResponse<PasajeroCheckinDTO> cambiarAsiento(
            @PathVariable Long id,
            @RequestParam String nuevoAsiento
    ) {
        PasajeroCheckinDTO dto = service.actualizarAsiento(id, nuevoAsiento);
        return new ApiResponse<>(dto != null, dto != null ? "Asiento actualizado" : "Pasajero no encontrado", dto);
    }

    @GetMapping("/boardingpass/{codigo}")
    public PasajeroCheckinDTO buscarPorBoarding(@PathVariable String codigo) {
        return service.buscarPorBoardingPass(codigo);
    }
}
