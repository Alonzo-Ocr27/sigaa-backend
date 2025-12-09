package com.sigaa.comercial;

import com.sigaa.common.ApiResponse;
import com.sigaa.seguridad.PermisosConst;
import com.sigaa.seguridad.SeguridadService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comercial/locales")
public class LocalComercialController {

    private final LocalComercialService service;
    private final SeguridadService seguridadService;

    public LocalComercialController(LocalComercialService service,
                                    SeguridadService seguridadService) {
        this.service = service;
        this.seguridadService = seguridadService;
    }

    // ==========================================================
    // CREAR LOCAL (requiere permiso)
    // ==========================================================
    @PostMapping("/crear")
    public ApiResponse<LocalComercial> crear(@RequestBody LocalComercial l,
                                             HttpServletRequest request) {

        Long usuarioId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(usuarioId, PermisosConst.COMERCIAL.LOCAL_EDITAR)) {
            return new ApiResponse<>(false,
                    "No tiene permiso para crear locales comerciales",
                    null);
        }

        LocalComercial creado = service.crear(l);
        return new ApiResponse<>(true, "Local creado", creado);
    }

    // ==========================================================
    // LISTAR (sin permisos)
    // ==========================================================
    @GetMapping("/listar")
    public List<LocalComercial> listar() {
        return service.listar();
    }

    // ==========================================================
    // OBTENER DETALLE (sin permisos)
    // ==========================================================
    @GetMapping("/{id}")
    public LocalComercial buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    // ==========================================================
    // ACTUALIZAR LOCAL (requiere permiso)
    // ==========================================================
    @PutMapping("/{id}")
    public ApiResponse<LocalComercial> actualizar(@PathVariable Long id,
                                                  @RequestBody LocalComercial datos,
                                                  HttpServletRequest request) {

        Long usuarioId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(usuarioId, PermisosConst.COMERCIAL.LOCAL_EDITAR)) {
            return new ApiResponse<>(false,
                    "No tiene permiso para actualizar locales comerciales",
                    null);
        }

        LocalComercial l = service.actualizar(id, datos);
        return new ApiResponse<>(l != null, l != null ? "Actualizado" : "No encontrado", l);
    }

    // ==========================================================
    // CAMBIAR ESTADO (requiere permiso)
    // ==========================================================
    @PutMapping("/{id}/estado")
    public ApiResponse<String> estado(@PathVariable Long id,
                                      @RequestParam String estado,
                                      HttpServletRequest request) {

        Long usuarioId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(usuarioId, PermisosConst.COMERCIAL.LOCAL_EDITAR)) {
            return new ApiResponse<>(false,
                    "No tiene permiso para cambiar estado de locales",
                    null);
        }

        boolean ok = service.cambiarEstado(id, estado);
        return new ApiResponse<>(ok, ok ? "Estado actualizado" : "Local no encontrado", null);
    }
}