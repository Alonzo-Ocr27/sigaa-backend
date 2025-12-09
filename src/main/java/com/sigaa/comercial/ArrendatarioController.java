package com.sigaa.comercial;

import com.sigaa.common.ApiResponse;
import com.sigaa.seguridad.PermisosConst;
import com.sigaa.seguridad.SeguridadService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comercial/arrendatarios")
public class ArrendatarioController {

    private final ArrendatarioService service;
    private final SeguridadService seguridadService;

    public ArrendatarioController(ArrendatarioService service,
                                  SeguridadService seguridadService) {
        this.service = service;
        this.seguridadService = seguridadService;
    }

    // ==========================================================
    // CREAR ARRENDATARIO (requiere permiso)
    // ==========================================================
    @PostMapping("/crear")
    public ApiResponse<Arrendatario> crear(@RequestBody Arrendatario a,
                                           HttpServletRequest request) {

        Long usuarioId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(usuarioId, PermisosConst.COMERCIAL.LOCAL_EDITAR)) {
            return new ApiResponse<>(false,
                    "No tiene permiso para crear arrendatarios",
                    null);
        }

        Arrendatario creado = service.crear(a);
        return new ApiResponse<>(true, "Arrendatario creado", creado);
    }

    // ==========================================================
    // LISTAR (sin permisos)
    // ==========================================================
    @GetMapping("/listar")
    public List<Arrendatario> listar() {
        return service.listar();
    }

    // ==========================================================
    // OBTENER DETALLE (sin permisos)
    // ==========================================================
    @GetMapping("/{id}")
    public Arrendatario buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    // ==========================================================
    // ACTUALIZAR ARRENDATARIO (requiere permiso)
    // ==========================================================
    @PutMapping("/{id}")
    public ApiResponse<Arrendatario> actualizar(@PathVariable Long id,
                                                @RequestBody Arrendatario datos,
                                                HttpServletRequest request) {

        Long usuarioId = (Long) request.getAttribute("usuarioId");

        if (!seguridadService.usuarioTienePermiso(usuarioId, PermisosConst.COMERCIAL.LOCAL_EDITAR)) {
            return new ApiResponse<>(false,
                    "No tiene permiso para actualizar arrendatarios",
                    null);
        }

        Arrendatario a = service.actualizar(id, datos);
        return new ApiResponse<>(a != null, a != null ? "Actualizado" : "No encontrado", a);
    }
}