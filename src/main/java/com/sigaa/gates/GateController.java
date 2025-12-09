package com.sigaa.gates;

import com.sigaa.common.ApiResponse;
import com.sigaa.seguridad.PermisosConst;
import com.sigaa.seguridad.SeguridadService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gates")
@CrossOrigin("*")
public class GateController {

    private final GateService gateService;
    private final GateAsignacionService asignacionService;
    private final SeguridadService seguridadService;

    public GateController(GateService gateService,
                          GateAsignacionService asignacionService,
                          SeguridadService seguridadService) {

        this.gateService = gateService;
        this.asignacionService = asignacionService;
        this.seguridadService = seguridadService;
    }

    // ============================================================
    // CREAR GATE
    // ============================================================
    @PostMapping("/crear")
    public ApiResponse<Gate> crear(@RequestBody Gate gate,
                                   HttpServletRequest request) {

        Long usuarioId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(usuarioId, PermisosConst.GATES.ESTADO)) {
            throw new RuntimeException("No tiene permiso para crear gates");
        }

        Gate creada = gateService.crear(gate);
        return new ApiResponse<>(true, "Gate creada correctamente", creada);
    }

    // ============================================================
    // LISTAR GATES (PÚBLICO)
    // ============================================================
    @GetMapping("/listar")
    public ApiResponse<List<Gate>> listar() {
        return new ApiResponse<>(true, "OK", gateService.listar());
    }

    // ============================================================
    // CAMBIAR ESTADO DE UNA GATE
    // ============================================================
    @PutMapping("/estado/{id}")
    public ApiResponse<String> estado(@PathVariable Long id,
                                      @RequestParam String estado,
                                      HttpServletRequest request) {

        Long usuarioId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(usuarioId, PermisosConst.GATES.ESTADO)) {
            throw new RuntimeException("No tiene permiso para cambiar estado de gates");
        }

        gateService.cambiarEstado(id, estado);

        return new ApiResponse<>(true, "Estado actualizado correctamente", null);
    }

    // ============================================================
    // ASIGNAR GATE A VUELO
    // ============================================================
    @PostMapping("/asignar")
    public ApiResponse<GateAsignacion> asignar(@RequestParam Long vueloId,
                                               @RequestParam String tipoAeronave,
                                               HttpServletRequest request) {

        Long usuarioId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(usuarioId, PermisosConst.GATES.ASIGNAR)) {
            throw new RuntimeException("No tiene permiso para asignar gates");
        }

        if (vueloId == null) {
            throw new RuntimeException("Debe enviar el ID del vuelo");
        }

        if (tipoAeronave == null || tipoAeronave.trim().isEmpty()) {
            throw new RuntimeException("Debe indicar el tipo de aeronave");
        }

        GateAsignacion asig = asignacionService.asignar(vueloId, tipoAeronave);

        if (asig == null) {
            throw new RuntimeException("No hay gate disponible compatible");
        }

        return new ApiResponse<>(true, "Gate asignada correctamente", asig);
    }

    // ============================================================
    // FINALIZAR ASIGNACIÓN Y LIBERAR GATE
    // ============================================================
    @PutMapping("/finalizar/{asignId}")
    public ApiResponse<String> finalizar(@PathVariable Long asignId,
                                         HttpServletRequest request) {

        Long usuarioId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(usuarioId, PermisosConst.GATES.ASIGNAR)) {
            throw new RuntimeException("No tiene permiso para finalizar asignaciones");
        }

        asignacionService.finalizar(asignId);

        return new ApiResponse<>(true, "Gate liberada correctamente", null);
    }
}