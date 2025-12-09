package com.sigaa.boarding;

import com.sigaa.common.ApiResponse;
import com.sigaa.seguridad.SeguridadService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boarding")
@CrossOrigin("*")
public class BoardingController {

    private final BoardingService service;
    private final SeguridadService seguridadService;

    public BoardingController(BoardingService service, SeguridadService seguridadService) {
        this.service = service;
        this.seguridadService = seguridadService;
    }

    @PostMapping("/scan")
    public ApiResponse<BoardingRegistro> escanear(
            HttpServletRequest request,
            @RequestParam String codigo) {

        Long userId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(userId, "BOARDING_ESCANEAR")) {
            throw new RuntimeException("No tiene permiso para escanear boarding pass");
        }

        try {
            BoardingRegistro r = service.escanear(codigo);
            return new ApiResponse<>(true, "Pasajero embarcado", r);
        } catch (RuntimeException e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @GetMapping("/vuelo/{vueloId}")
    public List<BoardingRegistro> listar(
            HttpServletRequest request,
            @PathVariable Long vueloId) {

        Long userId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(userId, "BOARDING_LISTAR")) {
            throw new RuntimeException("No tiene permiso para listar embarques");
        }

        return service.listarPorVuelo(vueloId);
    }

    @PutMapping("/{id}/cancelar")
    public ApiResponse<String> cancelar(
            HttpServletRequest request,
            @PathVariable Long id) {

        Long userId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(userId, "BOARDING_CANCELAR")) {
            throw new RuntimeException("No tiene permiso para cancelar embarques");
        }

        boolean ok = service.cancelar(id);
        return new ApiResponse<>(ok, ok ? "Embarque cancelado" : "Registro no encontrado", null);
    }
}
