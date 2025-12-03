package com.sigaa.brs;

import com.sigaa.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brs")
@CrossOrigin("*")
public class MaletaController {

    private final MaletaService service;

    public MaletaController(MaletaService service) {
        this.service = service;
    }

    // Registrar maleta (desde check-in o manual)
    @PostMapping("/maletas/registrar")
    public ApiResponse<Maleta> registrar(@RequestBody Maleta m) {
        try {
            Maleta creada = service.registrar(m);
            return new ApiResponse<>(true, "Maleta registrada", creada);
        } catch (RuntimeException e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    // Buscar por tag
    @GetMapping("/maletas/tag/{tagCode}")
    public Maleta buscarPorTag(@PathVariable String tagCode) {
        return service.buscarPorTag(tagCode);
    }

    // Listar por vuelo
    @GetMapping("/maletas/vuelo/{vueloId}")
    public List<Maleta> porVuelo(@PathVariable Long vueloId) {
        return service.listarPorVuelo(vueloId);
    }

    // Listar por pasajero
    @GetMapping("/maletas/pasajero/{pasajeroId}")
    public List<Maleta> porPasajero(@PathVariable Long pasajeroId) {
        return service.listarPorPasajero(pasajeroId);
    }

    // Cambiar estado (CARGADA, DESCARGADA, EN_BHS, etc.)
    @PutMapping("/maletas/{id}/estado")
    public ApiResponse<Maleta> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String estado
    ) {
        Maleta m = service.cambiarEstado(id, estado);
        return new ApiResponse<>(m != null, m != null ? "Estado actualizado" : "Maleta no encontrada", m);
    }

    // Actualizar ubicación
    @PutMapping("/maletas/{id}/ubicacion")
    public ApiResponse<Maleta> ubicar(
            @PathVariable Long id,
            @RequestParam String ubicacion
    ) {
        Maleta m = service.actualizarUbicacion(id, ubicacion);
        return new ApiResponse<>(m != null, m != null ? "Ubicación actualizada" : "Maleta no encontrada", m);
    }

    // Marcar EXTRAVIADA
    @PutMapping("/maletas/{id}/extraviada")
    public ApiResponse<Maleta> extraviada(@PathVariable Long id) {
        Maleta m = service.marcarExtraviada(id);
        return new ApiResponse<>(m != null, m != null ? "Maleta marcada como extraviada" : "Maleta no encontrada", m);
    }
}