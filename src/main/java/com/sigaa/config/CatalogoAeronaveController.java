package com.sigaa.config;

import com.sigaa.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config/aeronaves")
@CrossOrigin("*")
public class CatalogoAeronaveController {

    private final CatalogoAeronaveService service;

    public CatalogoAeronaveController(CatalogoAeronaveService service) {
        this.service = service;
    }

    // =====================================
    // CREAR
    // =====================================
    @PostMapping("/crear")
    public ApiResponse<CatalogoAeronave> crear(@RequestBody CatalogoAeronave a) {
        return new ApiResponse<>(true, "Aeronave creada", service.crear(a));
    }

    // =====================================
    // LISTAR
    // =====================================
    @GetMapping("/listar")
    public ApiResponse<List<CatalogoAeronave>> listar() {
        return new ApiResponse<>(true, "OK", service.listar());
    }

    // =====================================
    // EDITAR
    // =====================================
    @PutMapping("/editar/{id}")
    public ApiResponse<CatalogoAeronave> editar(
            @PathVariable Long id,
            @RequestBody CatalogoAeronave datos
    ) {
        return new ApiResponse<>(true, "Aeronave actualizada", service.actualizar(id, datos));
    }

    // =====================================
    // ELIMINAR
    // =====================================
    @DeleteMapping("/eliminar/{id}")
    public ApiResponse<String> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return new ApiResponse<>(
                true,
                "Aeronave eliminada",
                "OK"
        );
    }
}
