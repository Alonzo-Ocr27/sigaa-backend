package com.sigaa.boarding;

import com.sigaa.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boarding")
@CrossOrigin("*")
public class BoardingController {

    private final BoardingService service;

    public BoardingController(BoardingService service) {
        this.service = service;
    }

    @PostMapping("/scan")
    public ApiResponse<BoardingRegistro> escanear(@RequestParam String codigo) {
        try {
            BoardingRegistro r = service.escanear(codigo);
            return new ApiResponse<>(true, "Pasajero embarcado", r);
        } catch (RuntimeException e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @GetMapping("/vuelo/{vueloId}")
    public List<BoardingRegistro> listar(@PathVariable Long vueloId) {
        return service.listarPorVuelo(vueloId);
    }

    @PutMapping("/{id}/cancelar")
    public ApiResponse<String> cancelar(@PathVariable Long id) {
        boolean ok = service.cancelar(id);
        return new ApiResponse<>(ok, ok ? "Embarque cancelado" : "Registro no encontrado", null);
    }
}