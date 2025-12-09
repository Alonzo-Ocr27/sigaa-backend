package com.sigaa.comercial;

import com.sigaa.common.ApiResponse;
import com.sigaa.seguridad.PermisosConst;
import com.sigaa.seguridad.SeguridadService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/comercial/contratos")
public class ContratoComercialController {

    private final ContratoComercialService service;
    private final SeguridadService seguridadService;

    public ContratoComercialController(ContratoComercialService service,
                                       SeguridadService seguridadService) {
        this.service = service;
        this.seguridadService = seguridadService;
    }

    // ==========================================================
    // CREAR CONTRATO  (REQUIERE PERMISO)
    // ==========================================================
    @PostMapping("/crear")
    public ApiResponse<ContratoComercial> crear(@RequestParam Long localId,
                                                @RequestParam Long arrendatarioId,
                                                @RequestParam String inicio,
                                                @RequestParam String fin,
                                                HttpServletRequest request) {

        Long usuarioId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(usuarioId, PermisosConst.COMERCIAL.CONTRATO)) {
            return new ApiResponse<>(false,
                    "No tiene permiso para crear contratos",
                    null);
        }

        LocalDate fInicio = LocalDate.parse(inicio);
        LocalDate fFin = LocalDate.parse(fin);

        try {
            ContratoComercial c = service.crear(localId, arrendatarioId, fInicio, fFin);
            return new ApiResponse<>(true, "Contrato creado", c);
        } catch (RuntimeException e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    // ==========================================================
    // LISTAR CONTRATOS (público)
    // ==========================================================
    @GetMapping("/listar")
    public List<ContratoComercial> listar() {
        return service.listar();
    }

    // ==========================================================
    // OBTENER CONTRATO (público)
    // ==========================================================
    @GetMapping("/{id}")
    public ContratoComercial buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    // ==========================================================
    // FINALIZAR CONTRATO  (REQUIERE PERMISO)
    // ==========================================================
    @PutMapping("/{id}/finalizar")
    public ApiResponse<String> finalizar(@PathVariable Long id,
                                         HttpServletRequest request) {

        Long usuarioId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(usuarioId, PermisosConst.COMERCIAL.CONTRATO)) {
            return new ApiResponse<>(false,
                    "No tiene permiso para finalizar contratos",
                    null);
        }

        boolean ok = service.finalizar(id);
        return new ApiResponse<>(ok, ok ? "Contrato finalizado" : "Contrato no encontrado", null);
    }

    // ==========================================================
    // CANCELAR CONTRATO  (REQUIERE PERMISO)
    // ==========================================================
    @PutMapping("/{id}/cancelar")
    public ApiResponse<String> cancelar(@PathVariable Long id,
                                        HttpServletRequest request) {

        Long usuarioId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(usuarioId, PermisosConst.COMERCIAL.CONTRATO)) {
            return new ApiResponse<>(false,
                    "No tiene permiso para cancelar contratos",
                    null);
        }

        boolean ok = service.cancelar(id);
        return new ApiResponse<>(ok, ok ? "Contrato cancelado" : "Contrato no encontrado", null);
    }
}