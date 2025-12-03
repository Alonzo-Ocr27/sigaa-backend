package com.sigaa.vuelos;

import com.sigaa.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vuelos")
public class VueloController {

    private final VueloService service;

    public VueloController(VueloService service) {
        this.service = service;
    }

    // =========================================
    // CU-10 Crear vuelo
    // =========================================
    @PostMapping("/crear")
    public ApiResponse<Vuelo> crear(@RequestBody Vuelo vuelo) {
        Vuelo creado = service.crear(vuelo);
        return new ApiResponse<>(true, "Vuelo creado", creado);
    }

    // =========================================
    // CU-13 Consultar vuelo
    // =========================================
    @GetMapping("/{id}")
    public ApiResponse<Vuelo> buscar(@PathVariable Long id) {
        try {
            return new ApiResponse<>(true, "OK", service.buscar(id));
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    // =========================================
    // CU-11 Editar vuelo
    // =========================================
    @PutMapping("/editar/{id}")
    public ApiResponse<Vuelo> editar(@PathVariable Long id,
                                     @RequestBody Vuelo datos) {
        try {
            return new ApiResponse<>(true, "Vuelo actualizado",
                    service.actualizar(id, datos));
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    // =========================================
    // CU-12 Eliminar vuelo
    // =========================================
    @DeleteMapping("/eliminar/{id}")
    public ApiResponse<String> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return new ApiResponse<>(true, "Vuelo eliminado", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    // =========================================
    // CU-14 Cambiar estado
    // =========================================
    @PutMapping("/estado/{id}")
    public ApiResponse<Vuelo> cambiarEstado(@PathVariable Long id,
                                            @RequestParam String estado) {

        try {
            return new ApiResponse<>(true, "Estado cambiado",
                    service.cambiarEstado(id, estado));
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    // =========================================
    // CU-13 Listar vuelos
    // =========================================
    @GetMapping("/listar")
    public List<Vuelo> listar() {
        return service.listar();
    }
}