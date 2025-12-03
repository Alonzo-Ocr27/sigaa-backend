package com.sigaa.config;

import com.sigaa.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogos/aeronaves")
@CrossOrigin("*")
public class CatalogoAeronaveController {

    private final CatalogoAeronaveService service;

    public CatalogoAeronaveController(CatalogoAeronaveService service) {
        this.service = service;
    }

    @PostMapping("/crear")
    public ApiResponse<CatalogoAeronave> crear(@RequestBody CatalogoAeronave a) {
        return new ApiResponse<>(true, "Aeronave creada", service.crear(a));
    }

    @GetMapping("/listar")
    public List<CatalogoAeronave> listar() {
        return service.listar();
    }

    @PutMapping("/editar/{id}")
    public ApiResponse<CatalogoAeronave> editar(
            @PathVariable Long id,
            @RequestBody CatalogoAeronave datos
    ) {
        return new ApiResponse<>(true, "Aeronave actualizada", service.actualizar(id, datos));
    }

    @DeleteMapping("/eliminar/{id}")
    public ApiResponse<String> eliminar(@PathVariable Long id) {
        boolean ok = service.eliminar(id);
        return new ApiResponse<>(ok, ok ? "Aeronave eliminada" : "No encontrada", null);
    }
}