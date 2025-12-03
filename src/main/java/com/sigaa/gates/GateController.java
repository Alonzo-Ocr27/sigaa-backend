package com.sigaa.gates;

import com.sigaa.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gates")
public class GateController {

    private final GateService gateService;
    private final GateAsignacionService asignacionService;
    private final GateRepository gateRepo;

    public GateController(GateService gateService,
                          GateAsignacionService asignacionService,
                          GateRepository gateRepo) {
        this.gateService = gateService;
        this.asignacionService = asignacionService;
        this.gateRepo = gateRepo;
    }

    // ============================================================
    // CREAR GATE
    // ============================================================
    @PostMapping("/crear")
    public ApiResponse<Gate> crear(@RequestBody Gate gate) {

        // Validar duplicado
        if (gateRepo.existsByCodigo(gate.getCodigo())) {
            return new ApiResponse<>(false,
                    "Ya existe una gate con el código " + gate.getCodigo(),
                    null);
        }

        Gate creada = gateService.crear(gate);
        return new ApiResponse<>(true, "Gate creada correctamente", creada);
    }

    // ============================================================
    // LISTAR GATES
    // ============================================================
    @GetMapping("/listar")
    public List<Gate> listar() {
        return gateService.listar();
    }

    // ============================================================
    // CAMBIAR ESTADO
    // ============================================================
    @PutMapping("/estado/{id}")
    public ApiResponse<String> estado(
            @PathVariable Long id,
            @RequestParam String estado) {

        boolean ok = gateService.cambiarEstado(id, estado);

        return new ApiResponse<>(
                ok,
                ok ? "Estado actualizado" : "Gate no encontrada",
                null
        );
    }

    // ============================================================
    // ASIGNAR GATE
    // ============================================================
    @PostMapping("/asignar")
    public ApiResponse<GateAsignacion> asignar(
            @RequestParam Long vueloId,
            @RequestParam String tipoAeronave) {

        if (vueloId == null) {
            return new ApiResponse<>(false,
                    "Debe enviar el ID del vuelo",
                    null);
        }

        if (tipoAeronave == null || tipoAeronave.trim().isEmpty()) {
            return new ApiResponse<>(false,
                    "Debe indicar el tipo de aeronave",
                    null);
        }

        GateAsignacion asig = asignacionService.asignar(vueloId, tipoAeronave);

        return new ApiResponse<>(
                asig != null,
                asig != null ? "Gate asignada correctamente" : "No hay gate disponible compatible",
                asig
        );
    }

    // ============================================================
    // FINALIZAR ASIGNACIÓN
    // ============================================================
    @PutMapping("/finalizar/{asignId}")
    public ApiResponse<String> finalizar(@PathVariable Long asignId) {

        boolean ok = asignacionService.finalizar(asignId);

        return new ApiResponse<>(
                ok,
                ok ? "Gate liberada" : "Asignación no encontrada",
                null
        );
    }
}