package com.sigaa.comercial;

import com.sigaa.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/comercial/contratos")
public class ContratoComercialController {

    private final ContratoComercialService service;

    public ContratoComercialController(ContratoComercialService service) {
        this.service = service;
    }

    @PostMapping("/crear")
    public ApiResponse<ContratoComercial> crear(@RequestParam Long localId,
                                                @RequestParam Long arrendatarioId,
                                                @RequestParam String inicio,
                                                @RequestParam String fin) {

        LocalDate fInicio = LocalDate.parse(inicio);
        LocalDate fFin = LocalDate.parse(fin);

        try {
            ContratoComercial c = service.crear(localId, arrendatarioId, fInicio, fFin);
            return new ApiResponse<>(true, "Contrato creado", c);
        } catch (RuntimeException e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @GetMapping("/listar")
    public List<ContratoComercial> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ContratoComercial buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}/finalizar")
    public ApiResponse<String> finalizar(@PathVariable Long id) {
        boolean ok = service.finalizar(id);
        return new ApiResponse<>(ok, ok ? "Contrato finalizado" : "Contrato no encontrado", null);
    }

    @PutMapping("/{id}/cancelar")
    public ApiResponse<String> cancelar(@PathVariable Long id) {
        boolean ok = service.cancelar(id);
        return new ApiResponse<>(ok, ok ? "Contrato cancelado" : "Contrato no encontrado", null);
    }
}