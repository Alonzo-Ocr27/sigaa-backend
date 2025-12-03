package com.sigaa.comercial;

import com.sigaa.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comercial/locales")
public class LocalComercialController {

    private final LocalComercialService service;

    public LocalComercialController(LocalComercialService service) {
        this.service = service;
    }

    @PostMapping("/crear")
    public ApiResponse<LocalComercial> crear(@RequestBody LocalComercial l) {
        return new ApiResponse<>(true, "Local creado", service.crear(l));
    }

    @GetMapping("/listar")
    public List<LocalComercial> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public LocalComercial buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public ApiResponse<LocalComercial> actualizar(@PathVariable Long id,
                                                  @RequestBody LocalComercial datos) {
        LocalComercial l = service.actualizar(id, datos);
        return new ApiResponse<>(l != null, l != null ? "Actualizado" : "No encontrado", l);
    }

    @PutMapping("/{id}/estado")
    public ApiResponse<String> estado(@PathVariable Long id, @RequestParam String estado) {
        boolean ok = service.cambiarEstado(id, estado);
        return new ApiResponse<>(ok, ok ? "Estado actualizado" : "Local no encontrado", null);
    }
}
