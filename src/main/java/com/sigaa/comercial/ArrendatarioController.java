package com.sigaa.comercial;

import com.sigaa.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comercial/arrendatarios")
public class ArrendatarioController {

    private final ArrendatarioService service;

    public ArrendatarioController(ArrendatarioService service) {
        this.service = service;
    }

    @PostMapping("/crear")
    public ApiResponse<Arrendatario> crear(@RequestBody Arrendatario a) {
        return new ApiResponse<>(true, "Arrendatario creado", service.crear(a));
    }

    @GetMapping("/listar")
    public List<Arrendatario> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Arrendatario buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public ApiResponse<Arrendatario> actualizar(@PathVariable Long id,
                                                @RequestBody Arrendatario datos) {
        Arrendatario a = service.actualizar(id, datos);
        return new ApiResponse<>(a != null, a != null ? "Actualizado" : "No encontrado", a);
    }
}