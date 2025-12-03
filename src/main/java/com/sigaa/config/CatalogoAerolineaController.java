package com.sigaa.config;

import com.sigaa.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogos/aerolineas")
@CrossOrigin("*")
public class CatalogoAerolineaController {

    private final CatalogoAerolineaService service;

    public CatalogoAerolineaController(CatalogoAerolineaService service) {
        this.service = service;
    }

    @PostMapping("/crear")
    public ApiResponse<CatalogoAerolinea> crear(@RequestBody CatalogoAerolinea a) {
        return new ApiResponse<>(true, "Aerolinea creada", service.crear(a));
    }

    @GetMapping("/listar")
    public List<CatalogoAerolinea> listar() {
        return service.listar();
    }

    @PutMapping("/editar/{id}")
    public ApiResponse<CatalogoAerolinea> editar(
            @PathVariable Long id,
            @RequestBody CatalogoAerolinea datos
    ) {
        return new ApiResponse<>(true, "Aerolinea actualizada", service.actualizar(id, datos));
    }

    @DeleteMapping("/eliminar/{id}")
    public ApiResponse<String> eliminar(@PathVariable Long id) {
        boolean ok = service.eliminar(id);
        return new ApiResponse<>(ok, ok ? "Eliminada" : "No encontrada", null);
    }
}