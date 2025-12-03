package com.sigaa.comercial;

import com.sigaa.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/comercial/facturas")
public class FacturaComercialController {

    private final FacturaComercialService service;

    public FacturaComercialController(FacturaComercialService service) {
        this.service = service;
    }

    @PostMapping("/generar")
    public ApiResponse<FacturaComercial> generar(@RequestParam Long contratoId,
                                                 @RequestParam String fecha) {
        LocalDate f = LocalDate.parse(fecha);
        try {
            FacturaComercial fac = service.generarFactura(contratoId, f);
            return new ApiResponse<>(true, "Factura generada", fac);
        } catch (RuntimeException e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @GetMapping("/contrato/{contratoId}")
    public List<FacturaComercial> porContrato(@PathVariable Long contratoId) {
        return service.listarPorContrato(contratoId);
    }

    @PutMapping("/{id}/pagar")
    public ApiResponse<FacturaComercial> pagar(@PathVariable Long id) {
        FacturaComercial f = service.marcarPagada(id);
        return new ApiResponse<>(f != null, f != null ? "Factura pagada" : "Factura no encontrada", f);
    }

    @GetMapping("/pendientes")
    public List<FacturaComercial> pendientes() {
        return service.pendientes();
    }
} 