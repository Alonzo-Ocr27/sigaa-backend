package com.sigaa.brs;

import com.sigaa.common.ApiResponse;
import com.sigaa.seguridad.SeguridadService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brs")
@CrossOrigin("*")
public class MaletaController {

    private final MaletaService service;
    private final SeguridadService seguridadService;

    public MaletaController(MaletaService service, SeguridadService seguridadService) {
        this.service = service;
        this.seguridadService = seguridadService;
    }

    // ======================================================
    //                 REGISTRAR MALETA
    //                 Permiso: BRS_REGISTRAR
    // ======================================================
    @PostMapping("/maletas/registrar")
    public ApiResponse<Maleta> registrar(
            HttpServletRequest request,
            @RequestBody Maleta m) {

        Long userId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(userId, "BRS_REGISTRAR")) {
            throw new RuntimeException("No tiene permiso para registrar maletas");
        }

        try {
            Maleta creada = service.registrar(m);
            return new ApiResponse<>(true, "Maleta registrada", creada);
        } catch (RuntimeException e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    // ======================================================
    //               BUSCAR MALETA POR TAG
    //               Permiso: BRS_LISTAR
    // ======================================================
    @GetMapping("/maletas/tag/{tagCode}")
    public Maleta buscarPorTag(
            HttpServletRequest request,
            @PathVariable String tagCode) {

        Long userId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(userId, "BRS_LISTAR")) {
            throw new RuntimeException("No tiene permiso para consultar maletas");
        }

        return service.buscarPorTag(tagCode);
    }

    // ======================================================
    //            LISTAR MALETAS POR VUELO
    //            Permiso: BRS_LISTAR
    // ======================================================
    @GetMapping("/maletas/vuelo/{vueloId}")
    public List<Maleta> porVuelo(
            HttpServletRequest request,
            @PathVariable Long vueloId) {

        Long userId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(userId, "BRS_LISTAR")) {
            throw new RuntimeException("No tiene permiso para listar maletas");
        }

        return service.listarPorVuelo(vueloId);
    }

    // ======================================================
    //            LISTAR MALETAS POR PASAJERO
    //            Permiso: BRS_LISTAR
    // ======================================================
    @GetMapping("/maletas/pasajero/{pasajeroId}")
    public List<Maleta> porPasajero(
            HttpServletRequest request,
            @PathVariable Long pasajeroId) {

        Long userId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(userId, "BRS_LISTAR")) {
            throw new RuntimeException("No tiene permiso para listar maletas por pasajero");
        }

        return service.listarPorPasajero(pasajeroId);
    }

    // ======================================================
    //               CAMBIAR ESTADO DE MALETA
    //               Permiso: BRS_CAMBIAR_ESTADO
    // ======================================================
    @PutMapping("/maletas/{id}/estado")
    public ApiResponse<Maleta> cambiarEstado(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestParam String estado) {

        Long userId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(userId, "BRS_CAMBIAR_ESTADO")) {
            throw new RuntimeException("No tiene permiso para cambiar el estado de la maleta");
        }

        Maleta m = service.cambiarEstado(id, estado);
        return new ApiResponse<>(m != null, m != null ? "Estado actualizado" : "Maleta no encontrada", m);
    }

    // ======================================================
    //               CAMBIAR UBICACIÓN
    //               Permiso: BRS_CAMBIAR_UBICACION
    // ======================================================
    @PutMapping("/maletas/{id}/ubicacion")
    public ApiResponse<Maleta> ubicar(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestParam String ubicacion) {

        Long userId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(userId, "BRS_CAMBIAR_UBICACION")) {
            throw new RuntimeException("No tiene permiso para actualizar la ubicación de la maleta");
        }

        Maleta m = service.actualizarUbicacion(id, ubicacion);
        return new ApiResponse<>(m != null, m != null ? "Ubicación actualizada" : "Maleta no encontrada", m);
    }

    // ======================================================
    //                MARCAR COMO EXTRAVIADA
    //                Permiso: BRS_EXTRAVIAR
    // ======================================================
    @PutMapping("/maletas/{id}/extraviada")
    public ApiResponse<Maleta> extraviada(
            HttpServletRequest request,
            @PathVariable Long id) {

        Long userId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(userId, "BRS_EXTRAVIAR")) {
            throw new RuntimeException("No tiene permiso para marcar maletas como extraviadas");
        }

        Maleta m = service.marcarExtraviada(id);
        return new ApiResponse<>(m != null, m != null ? "Maleta marcada como extraviada" : "Maleta no encontrada", m);
    }
}