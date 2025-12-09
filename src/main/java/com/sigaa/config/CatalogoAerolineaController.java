package com.sigaa.config;

import com.sigaa.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config/aerolineas")
@CrossOrigin("*")
public class CatalogoAerolineaController {

    private final CatalogoAerolineaService service;

    public CatalogoAerolineaController(CatalogoAerolineaService service) {
        this.service = service;
    }

    // =======================================
    // CREAR
    // =======================================
    @PostMapping("/crear")
    public ApiResponse<CatalogoAerolinea> crear(@RequestBody CatalogoAerolinea a) {
        return new ApiResponse<>(
                true,
                "Aerolínea creada",
                service.crear(a)
        );
    }

    // =======================================
    // LISTAR
    // =======================================
    @GetMapping("/listar")
    public ApiResponse<List<CatalogoAerolinea>> listar() {
        return new ApiResponse<>(
                true,
                "OK",
                service.listar()
        );
    }

    // =======================================
    // EDITAR
    // =======================================
    @PutMapping("/editar/{id}")
    public ApiResponse<CatalogoAerolinea> editar(
            @PathVariable Long id,
            @RequestBody CatalogoAerolinea datos
    ) {
        return new ApiResponse<>(
                true,
                "Aerolínea actualizada",
                service.actualizar(id, datos)
        );
    }

    // =======================================
    // ELIMINAR
    // =======================================
    @DeleteMapping("/eliminar/{id}")
    public ApiResponse<String> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return new ApiResponse<>(
                true,
                "Aerolinea eliminada",
                "OK"
        );
    }
}
