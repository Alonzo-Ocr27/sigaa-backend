package com.sigaa.comercial;

import com.sigaa.common.ApiResponse;
import com.sigaa.seguridad.PermisosConst;
import com.sigaa.seguridad.SeguridadService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/comercial/facturas")
public class FacturaComercialController {

    private final FacturaComercialService service;
    private final SeguridadService seguridadService;

    public FacturaComercialController(FacturaComercialService service,
                                      SeguridadService seguridadService) {
        this.service = service;
        this.seguridadService = seguridadService;
    }

    // ==========================================================
    // GENERAR FACTURA (REQUIERE PERMISO)
    // ==========================================================
    @PostMapping("/generar")
    public ApiResponse<FacturaComercial> generar(@RequestParam Long contratoId,
                                                 @RequestParam String fecha,
                                                 HttpServletRequest request) {

        Long usuarioId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(usuarioId, PermisosConst.COMERCIAL.FACTURAR)) {
            return new ApiResponse<>(false,
                    "No tiene permiso para generar facturas",
                    null);
        }

        LocalDate f = LocalDate.parse(fecha);

        try {
            FacturaComercial fac = service.generarFactura(contratoId, f);
            return new ApiResponse<>(true, "Factura generada", fac);
        } catch (RuntimeException e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    // ==========================================================
    // LISTAR FACTURAS POR CONTRATO (público)
    // ==========================================================
    @GetMapping("/contrato/{contratoId}")
    public List<FacturaComercial> porContrato(@PathVariable Long contratoId) {
        return service.listarPorContrato(contratoId);
    }

    // ==========================================================
    // PAGAR FACTURA (REQUIERE PERMISO)
    // ==========================================================
    @PutMapping("/{id}/pagar")
    public ApiResponse<FacturaComercial> pagar(@PathVariable Long id,
                                               HttpServletRequest request) {

        Long usuarioId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(usuarioId, PermisosConst.COMERCIAL.PAGAR)) {
            return new ApiResponse<>(false,
                    "No tiene permiso para pagar facturas",
                    null);
        }

        FacturaComercial f = service.marcarPagada(id);
        return new ApiResponse<>(f != null,
                f != null ? "Factura pagada" : "Factura no encontrada",
                f);
    }

    // ==========================================================
    // LISTAR FACTURAS PENDIENTES (público)
    // ==========================================================
    @GetMapping("/pendientes")
    public List<FacturaComercial> pendientes() {
        return service.pendientes();
    }
}